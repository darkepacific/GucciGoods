package com.revature.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Account;
import com.revature.beans.Item;
import com.revature.services.AccountService;
import com.revature.services.ItemService;


@RestController
@RequestMapping(value="/item")
@CrossOrigin(origins="http://localhost:4200")
public class ItemController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private AccountService as;
	
	private static Logger logger = Logger.getLogger(LoginController.class);

	@RequestMapping(method=RequestMethod.GET)
	public Item getItem(@RequestParam("id") String itemId, HttpSession session)
	{
		int id = Integer.parseInt(itemId);
		Item i = itemService.getItemById(id);
		logger.info("Id: " + id);

		if(i != null) {

			session.setAttribute("item", i);
			System.out.println(i);
			return i;
		}

		return null;
	}

	@RequestMapping(method=RequestMethod.GET, value="/all")
	public List<Item> getItems(HttpSession session) {
		List<Item> items = new ArrayList<Item>();
		items = itemService.getItems();
		logger.info("\n \n" + items);
		//session.setAttribute("loggedAccount", u);
		return items;
	}
	
	@RequestMapping(value="/user/{id}", method=RequestMethod.GET)
	public List<Item> getItemsByUser(@PathVariable("id") int userId) {
	    return itemService.getItemsByUser(userId);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/buy")
	public Item buyItem(@RequestParam("id") Integer itemId, @RequestParam("name") String name, 
						@RequestParam("quantity") Integer quantity, HttpSession session)
	{
		//Account a = as.getAccountById(accountid);
		
		//Item i = is.getItemById(id);
		List<Item> items = new ArrayList<Item>();
		Item it = itemService.getItemById(itemId);
		
		
		items = itemService.getItemsByName(name);
		if(quantity > items.size()) {
			return null;
		}
		
		if(quantity < 1) {
			for(int i = 0; i < quantity; i++) {
				it = items.get(i);
				it.setSold(1);
				itemService.update(it);
			}
		}else {
			it.setSold(1);
			itemService.update(it);
		}
		
		//System.out.println(id);
		return it;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/search")
	public List<Item> searchItem(@RequestParam("query") String query, HttpSession session) {
	    // Retrieve all items from the database
	    List<Item> items = itemService.getItems();
	    logger.info("Query: " + query);

	    // Normalize the query string
	    String normalizedQuery = normalize(query);
	    String[] queryTokens = normalizedQuery.split("\\s+");

	    // Use a simple scored item wrapper
	    List<ScoredItem> scoredItems = new ArrayList<>();

	    // For each item, compute a score based on matches in the name and tags
	    for (Item item : items) {
	        String normalizedName = normalize(item.getName());
	        String normalizedTags = normalize(item.getTags());
	        String normalizedDescription = normalize(item.getItem_description());
	        String[] nameTokens = normalizedName.split("\\s+");
	        String[] tagTokens = normalizedTags.split("\\s+");
	        String[] decriptionTokens = normalizedDescription.split("\\s+");

	        int score = 0;

	     // 🔹 Direct name & tag matching
	        for (String qToken : queryTokens) {
	            for (String token : nameTokens) {
	                if (isMatch(qToken, token)) score += 10; // Exact name match
//	                else if (fuzzyMatch(qToken, token)) score += 5; // Near match (e.g. ignoring trailing "s")
	            }
	            for (String token : tagTokens) {
	                if (isMatch(qToken, token)) score += 7; // Exact tag match
//	                else if (fuzzyMatch(qToken, token)) score += 3; // Near Match in tags
	            }
	            for (String token : decriptionTokens) {
	                if (isMatch(qToken, token)) score += 7; // Exact description match
//	                else if (fuzzyMatch(qToken, token)) score += 3; // Near Match in tags
	            }
	        }

	        // Bonus: if the entire query appears in the name or tags, add extra points
	        if (normalizedName.contains(normalizedQuery) || normalizedTags.contains(normalizedQuery)) {
	            score += 25;
	        }

	        // 🔹 Synonym-based matching: if the query contains "fashion" and the item name contains "gucci" or similar, add bonus points. 
	        // Compute bonus based on the normalized item name and normalized query.
	        Map<String, String[]> synonymMapping = getSynonymMapping();
	        score += computeSynonymBonus(queryTokens, normalizedName, synonymMapping, 10);
	        score += computeSynonymBonus(queryTokens, normalizedTags, synonymMapping, 8);

	        // 🔹 Special Handling for Books
	        if (normalizedQuery.contains("book") || normalizedQuery.contains("books")) {
	            if (normalizedTags.contains("book") || normalizedTags.contains("novel") || normalizedTags.contains("kindle")) {
	                score += 15;  // Strong match for book-related tags
	            } else if (normalizedName.contains("kindle") || normalizedName.contains("paperwhite")  ) {
	                score -= 5;  // Reduce priority for Kindle **devices**
	            } else if (normalizedTags.contains("classic") || normalizedTags.contains("educational")) {
	                score -= 10; // Prevent unrelated "classics" from ranking high
	            }
	            if (normalizedName.contains("macbook") || normalizedTags.contains("macbook")) {
	            	score -= 20;
	            }
	        }
	        
	        // 🔹 Special Handling for Games
	        if (normalizedQuery.contains("game") || normalizedQuery.contains("games")) {
	            if (normalizedName.contains("tv")) {
	                score -= 10;
	            }
	            if (normalizedTags.contains("tv") || normalizedTags.contains("movies")) {
	                score -= 10;
	            }
	        }
	        
	     // 🔹 Special Handling for Kithchen
	        if (normalizedQuery.contains("kitchen")) {
	            if (normalizedName.contains("kit") && !(normalizedName.contains("kitchen"))) {
	                score -= 10;
	            }
	            if (normalizedTags.contains("kit") && !(normalizedTags.contains("kitchen"))) {
	                score -= 10;
	            }
	        }
	        
	        // 🔹 Special Handling for Macbooks
	        if (normalizedQuery.contains("macbook")) {
	            if (normalizedName.contains("book")) {
	                score -= 10;
	            }
	            if (normalizedTags.contains("book")) {
	                score -= 10;
	            }
	        }
	        
	        // 🔹 Special Handling for Health
	        if (normalizedQuery.contains("health")) {
	            if (normalizedName.contains("book")) {
	                score -= 20;
	            }
	            if (normalizedTags.contains("book")) {
	                score -= 20;
	            }
	        }

	        
	        // 🔹 Special Handling for Sports
	        if (normalizedQuery.contains("sports") || normalizedQuery.contains("sport")) {
	            if (normalizedTags.contains("game") && 
	                !(normalizedTags.contains("team") || normalizedTags.contains("competition") || normalizedTags.contains("match") || normalizedTags.contains("tournament"))) {
	                score -= 10; // Pushes generic "games" down
	            }

	            if (normalizedName.contains("chess") || normalizedTags.contains("chess")) {
	                score -= 15; // Ensures Chess does not rank under sports
	            }

	            if (normalizedName.contains("TV") || normalizedName.contains("monitor") || normalizedName.contains("screen")) {
	                score -= 15; // Prevents Smart TVs from appearing under sports
	            }
	        }
	        
	     // 🔹 Special Handling for Automotive and Car
	        if (normalizedQuery.contains("automotive") || normalizedQuery.contains("car")) {
	            if (normalizedName.contains("care") || normalizedName.contains("bear")) {
	                score -= 15;
	            }
	            if (normalizedTags.contains("care")) {
	                score -= 20;
	            }
	        }

	        // Only include items that meet a certain confidence score
	        if (score > 10) {
	            scoredItems.add(new ScoredItem(item, score));
	        }
	    }

	    // Sort scored items in descending order (highest score first)
	    scoredItems.sort((a, b) -> b.score - a.score);

	    // Extract the items from scoredItems
	    List<Item> results = scoredItems.stream()
	                                    .map(scoredItem -> scoredItem.item)
	                                    .collect(Collectors.toList());

	    return results;
	}

	/* Helper class to wrap an item with its computed score */
	private static class ScoredItem {
	    Item item;
	    int score;
	    ScoredItem(Item item, int score) {
	        this.item = item;
	        this.score = score;
	    }
	}

	/* Normalize string: lower-case, trim, remove punctuation */
	private String normalize(String input) {
	    if (input == null) return "";
	    // Replace "&" with "and" before removing special characters
	    input = input.replace("&", "and");
	    return input.toLowerCase().replaceAll("[^a-z0-9\\s]", "").trim();
	}

	/* Checks for an exact match or an exact match after stripping a trailing 's' */
	private boolean isMatch(String token1, String token2) {
	    if (token1.equals(token2)) return true;
	    // If token ends with 's', check without it
	    if (token1.endsWith("s") && token1.substring(0, token1.length()-1).equals(token2)) return true;
	    if (token2.endsWith("s") && token2.substring(0, token2.length()-1).equals(token1)) return true;
	    return false;
	}

	/* A simple fuzzy match: you can implement a Levenshtein distance check if desired */
	private boolean fuzzyMatch(String token1, String token2) {
	    // For simplicity, consider a match if the tokens are similar ignoring a trailing 's'
	    return isMatch(token1, token2);
	}

	/* Checks if any token in the query equals a given target, and if so, whether the item text contains any of the synonyms */
	private boolean containsSynonym(String[] queryTokens, String itemText, String target, String[] synonyms) {
	    for (String qToken : queryTokens) {
	        if (qToken.equals(target)) {
	            for (String synonym : synonyms) {
	                if (itemText.contains(synonym)) {
	                    return true;
	                }
	            }
	        }
	    }
	    return false;
	}
	
	/**
	 * Returns a mapping from key query terms to their synonyms.
	 */
	private Map<String, String[]> getSynonymMapping() {
	    Map<String, String[]> synonyms = new HashMap<>();
	    
	    synonyms.put("fashion", new String[]{
	        "gucci", "prada", "h&m", "zara", "nike", "adidas", "puma", "supreme",
	        "balenciaga", "versace", "chanel", "louis vuitton", "burberry", "fendi", 
	        "dior", "apparel", "clothes", "couture", "designer", "style",
	        "clothing", "wardrobe", "trendy", "runway", "haute couture",
	        "boutique", "vintage", "outfit", "wear", "luxury", "accessories",
	        "footwear", "handbag", "jewelry", "brand", "trend", "aesthetic",
	        "beauty"
	    });
	    
	    synonyms.put("man", new String[]{
	    		"men", "mens", "adult", "male"
		    });
	    
	    synonyms.put("men", new String[]{
	    		"man", "mens", "adult", "male"
			});
	    
	    synonyms.put("women", new String[]{
	    		"woman", "womens", "adult", "female"
			});
	    
	    synonyms.put("woman", new String[]{
	    		"women", "womens", "adult", "female"
			});

	    synonyms.put("kitchen", new String[]{
	        "grocery", "food", "groceries", "cook", "cooking", "culinary",
	        "baking", "dining", "recipe", "chef", "restaurant", "homemade",
	        "kitchenware", "cookware", "utensils", "oven", "stove", "pantry",
	        "spices", "cuisine", "meals", "dish", "grill", "cutlery", "mixer", "microwave",
	        "dishwasher", "pots", "pans", "nonstick", "silverware", "tableware", "knife"
	    });

	    synonyms.put("groceries", new String[]{
	        "grocery", "food", "farm", "cook", "cooking", "culinary",
	        "baking", "dining", "recipe", "chef", "restaurant", "homemade",
	        "fresh", "oven", "stove", "pantry", "spices", "cuisine",
	        "meals", "dish", "grill", "organic", "produce", "vegetables", 
	        "fruits", "dairy", "meat", "beverages", "snacks", "frozen",
	        "canned", "grains", "pasta", "condiments"
	    });

	    synonyms.put("sports", new String[]{
	        "sport", "athletic", "exercise", "fitness", "recreation",
	        "workout", "training", "gym", "game", "team", "league",
	        "competition", "running", "cycling", "football", "basketball",
	        "tennis", "baseball", "soccer", "hockey", "yoga", "outdoor",
	        "marathon", "athlete", "stadium", "weights", "dumbbells", "treadmill", 
	        "resistance bands", "nike", "adidas", "puma", "reebok"
	    });

	    synonyms.put("electronics", new String[]{
	        "gadget", "device", "tech", "technology", "appliance", "computer",
	        "laptop", "smartphone", "tablet", "camera", "headphones",
	        "speaker", "gaming", "monitor", "television", "wearable",
	        "charger", "cable", "hardware", "processor", "console", "apple",
	        "samsung", "dell", "hp", "lenovo", "sony", "huawei", "microsoft",
	        "google", "amazon", "fire tv", "kindle", "smart home", "wifi",
	        "battery", "bluetooth"
	    });

	    synonyms.put("home", new String[]{
	        "house", "apartment", "living", "furniture", "decor", "interior",
	        "bedroom", "bathroom", "kitchen", "garden", "homeware", "renovation",
	        "lighting", "appliance", "flooring", "cushions", "comfort", "space",
	        "architecture", "property", "real estate", "curtains", "sofa",
	        "carpet", "mattress", "bed frame", "sheets", "pillow", "rug"
	    });

	    synonyms.put("beauty", new String[]{
	        "cosmetics", "skincare", "makeup", "fragrance", "perfume",
	        "lotion", "self-care", "spa", "wellness", "grooming",
	        "haircare", "lipstick", "foundation", "mascara", "nail polish",
	        "soap", "serum", "moisturizer", "sunscreen", "estee lauder",
	        "clinique", "loreal", "maybelline", "nars", "fenty", "dior", 
	        "lancome", "garnier", "olay", "nivea", "spray"
	    });
	    
	    synonyms.put("cologne", new String[]{
		        "eau", "spray", "scent", "perfume"
		    });

	    synonyms.put("automotive", new String[]{
	        "car", "vehicle", "motor", "engine", "truck", "auto",
	        "transport", "repair", "oil", "battery", "brakes",
	        "fuel", "garage", "driving", "tires", "dealership",
	        "sedan", "suv", "road", "gear", "honda", "toyota",
	        "bmw", "audi", "mercedes", "ford", "chevrolet", "volkswagen",
	        "nissan", "tesla", "jeep"
	    });
	    
	    synonyms.put("auto", new String[]{
		        "car", "automotive", "vehicle", "motor", "engine", "truck", "auto",
		        "transport", "repair", "oil", "battery", "brakes",
		        "fuel", "garage", "driving", "tires", "dealership",
		        "sedan", "suv", "road", "gear", "honda", "toyota",
		        "bmw", "audi", "mercedes", "ford", "chevrolet", "volkswagen",
		        "nissan", "tesla", "jeep"
		});

	    synonyms.put("health", new String[]{
	        "wellness", "fitness", "medicine", "nutrition", "supplements",
	        "vitamins", "hospital", "clinic", "therapy", "mental health",
	        "doctor", "nurse", "workout", "hygiene", "sanitation",
	        "immune", "detox", "diet", "herbal", "organic", "cbd",
	        "first aid", "medication", "pharmacy", "surgical", "mask",
	        "bandage", "thermometer", "blood pressure", "stethoscope", "protection",
	        "medical"
	    });

	    synonyms.put("grocery", new String[]{
	        "supermarket", "food", "pantry", "meals", "produce",
	        "vegetables", "fruits", "dairy", "meat", "beverages",
	        "snacks", "frozen", "organic", "drinks", "bakery",
	        "canned", "grains", "pasta", "condiments", "beef",
	        "chicken", "fish", "seafood", "milk", "eggs", "butter",
	        "cheese", "yogurt", "bread", "cookies", "soda", "juice"
	    });
	    
	    // 🔹 Toys
	    synonyms.put("toys", new String[]{
	        "games", "board games", "video games", "puzzles", "lego", 
	        "dolls", "action figures", "playset", "fun", "collectibles", "plush", 
	        "teddy bear", "stuffed animals", "figures", "toy cars", "hot wheels", 
	        "rc cars", "remote control", "model kits", "building blocks", "playhouse",
	        "jigsaw puzzle", "card games", "strategy games", "kids toys", "gaming", 
	        "tabletop", "party games", "rpg", "miniatures", "chess", "monopoly",
	        "nintendo", "playstation", "xbox", "gaming console", "boardgame",
	        "educational toys", "learning games", "puzzles", "wooden toys", "science kits"
	    });
	    
	    synonyms.put("games", new String[]{
		        "toys", "board games", "video games", "puzzles", "lego", "gaming", 
		        "dolls", "action figures", "playset", "fun", "collectibles", "plush", 
		        "teddy bear", "stuffed animals", "figures", "toy cars", "hot wheels", 
		        "rc cars", "remote control", "model kits", "building blocks", "playhouse",
		        "jigsaw puzzle", "card games", "strategy games", "kids toys", "gaming", 
		        "tabletop", "party games", "rpg", "miniatures", "chess", "monopoly",
		        "nintendo", "playstation", "xbox", "gaming console", "boardgame",
		        "educational toys", "learning games", "puzzles", "wooden toys", "science kits"
		    });

	    // 🔹 Books
	    synonyms.put("books", new String[]{
	        "novel", "fiction", "nonfiction", "mystery", "thriller", "biography", 
	        "autobiography", "memoir", "history", "textbook", "education", "learning",
	        "literature", "poetry", "fantasy", "science fiction", "sci-fi", "romance", 
	        "classic", "bestseller", "author", "paperback", "hardcover", "ebook", "audiobook", 
	        "self-help", "business books", "philosophy", "young adult", "children’s books", 
	        "graphic novel", "manga", "comic book", "cookbook", "encyclopedia", "library",
	        "amazon kindle", "barnes & noble", "new york times bestseller", "bookstore",
	        "kindle books", "storytelling", "narrative", "literary", "manuscript"
	    });
	    
	    synonyms.put("outdoors", new String[]{
	            "nature", "wilderness", "adventure", "hiking", "camping", "backpacking", 
	            "trekking", "trails", "mountains", "forest", "woods", "national park", 
	            "state park", "wildlife", "landscape", "exploration", "scenic", "fresh air", 
	            "outdoor recreation", "survival", "bushcraft", "campfire", "fishing", "hunting", 
	            "boating", "kayaking", "canoeing", "rafting", "rock climbing", "bouldering", 
	            "mountaineering", "skiing", "snowboarding", "snowshoeing", "cycling", "biking", 
	            "birdwatching", "stargazing", "ecotourism", "conservation", "trail running", 
	            "orienteering", "geocaching", "wild camping", "caving", "glamping", "off-grid", 
	            "overlanding", "bushwalking", "outdoor survival", "firewood", "wilderness skills",
	            "prepper", "foraging", "nature photography"
	            });
	    
	    synonyms.put("nature", new String[]{
	            "outdoors", "wilderness", "adventure", "hiking", "camping", "backpacking", 
	            "trekking", "trails", "mountains", "forest", "woods", "national park", 
	            "state park", "wildlife", "landscape", "exploration", "scenic", "fresh air", 
	            "outdoor recreation", "survival", "bushcraft", "campfire", "fishing", "hunting", 
	            "boating", "kayaking", "canoeing", "rafting", "rock climbing", "bouldering", 
	            "mountaineering", "skiing", "snowboarding", "snowshoeing", "cycling", "biking", 
	            "birdwatching", "stargazing", "ecotourism", "conservation", "trail running", 
	            "orienteering", "geocaching", "wild camping", "caving", "glamping", "off-grid", 
	            "overlanding", "bushwalking", "outdoor survival", "firewood", "wilderness skills",
	            "prepper", "foraging", "nature photography", "land navigation", "wilderness therapy"
	    });
	    
	    synonyms.put("camping", new String[]{
	            "outdoors", "wilderness", "adventure", "hiking", "camping", "backpacking", 
	            "trekking", "trails", "mountains", "forest", "woods", "national park", 
	            "state park", "wildlife", "landscape", "exploration", "scenic", "fresh air", 
	            "outdoor recreation", "survival", "bushcraft", "campfire", "fishing", "hunting", 
	            "boating", "kayaking", "canoeing", "rafting", "rock climbing", "bouldering", 
	            "mountaineering", "skiing", "snowboarding", "snowshoeing", "cycling", "biking", 
	            "birdwatching", "stargazing", "ecotourism", "conservation", "trail running", 
	            "orienteering", "geocaching", "wild camping", "caving", "glamping", "off-grid", 
	            "overlanding", "bushwalking", "outdoor survival", "firewood", "wilderness"
	    });


	    return synonyms;
	}
	
	/**
	 * Computes bonus points for an item based on synonym matching.
	 *
	 * @param queryTokens Tokens from the query.
	 * @param itemText    The normalized text (name or tags) of the item.
	 * @param mapping     The synonyms mapping.
	 * @return Bonus score.
	 */
	private int computeSynonymBonus(String[] queryTokens, String itemText, Map<String, String[]> mapping, int bonusAmount) {
	    int bonus = 0;
	    for (Map.Entry<String, String[]> entry : mapping.entrySet()) {
	        String key = entry.getKey();
	        String[] synonyms = entry.getValue();
	        for (String qToken : queryTokens) {
	            // If the query contains the key term, check for synonyms in the item text.
	            if (qToken.equals(key)) {
	                for (String syn : synonyms) {
	                    if (itemText.contains(syn)) {
	                        bonus += bonusAmount;
	                        break; // Only add bonus once per key.
	                    }
	                }
	            }
	        }
	    }
	    return bonus;
	}


}
