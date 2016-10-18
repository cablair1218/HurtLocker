package blair.carina.HurtLocker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseData {

    private String output = "";
    private String[] groceryObjects;
    private ArrayList<GroceryItem> groceryItems = new ArrayList<>();
    private ArrayList<String> foodNames = new ArrayList<>();


    public String outputText(String str){
        jerkSonParse(str);
        for (GroceryItem groceryItem : groceryItems) {
            String name = formatNamePrint(groceryItem);
            String price = formatPriceCount(groceryItem);
            String total = formatTotalItems(groceryItem);
            output += name + total + "\n=============\t\t=============\n"+ price + "\n";
        }
        String error = formatError(calculateError());
        output+= error;
        return output;

    }

    public void jerkSonParse(String str){
        groceryObjects = str.split("##");
        parseInfo(groceryObjects);
    }

    public void parseInfo(String[] arrGroceryObjects){
        for (String s: arrGroceryObjects) {
            String parse = "((?i)\\bname:\\b)(\\w+)([;@^*%!])(\\b(?i)price:\\b)(\\d.\\d{2})([;@^*%!])((?i)\\btype:\\b)(\\w+)([;@^*%!])(\\b(?i)expiration:\\b)(\\d/\\d{2}/\\d{4})";
            Pattern pattern = Pattern.compile(parse);
            Matcher matcher = pattern.matcher(s);
            while(matcher.find()){
                String name = matcher.group(2);
                name = formatName(name);
                if(foodNames.contains(name)) {
                    duplicateItems(name, matcher.group(5));
                }
                else {
                    GroceryItem item = new GroceryItem(name, matcher.group(5), matcher.group(8), matcher.group(11));
                    groceryItems.add(item);
                    foodNames.add(name);
                    item.getPriceCount().put(matcher.group(5),1);
                }
            }

        }
    }

    public String formatName(String name){
        name = name.replaceAll("\\d+","o");
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public int calculateError(){
        int totalItems = 0;
        for(GroceryItem g: groceryItems){
            for(int i : g.getPriceCount().values()){
                totalItems += i;
            }

        }
        return groceryObjects.length - totalItems;
    }

    public String formatError(int error){
        return String.format("Errors\t\t\t\tseen: %d times", error);

    }

    public String formatTotalItems(GroceryItem groceryItem){
        HashMap<String, Integer> priceCount;
        int total = 0;
        priceCount = groceryItem.getPriceCount();
        Object[] prices = priceCount.keySet().toArray();
        for (int j = 0; j < priceCount.size(); j++){
            total += priceCount.get(prices[j]);
        }
        return String.format("seen: %d times", total);
    }

    public String formatNamePrint(GroceryItem groceryItem){
        return String.format("name:\t%s\t\t", groceryItem.getName());
    }

    public String formatPriceCount(GroceryItem groceryItem){
        HashMap<String, Integer> priceCount;
        String price = "";
        priceCount = groceryItem.getPriceCount();
        Object[] prices = priceCount.keySet().toArray();
        for (int j = 0; j < priceCount.size(); j++){
            price += String.format("Price:\t%s\t\tseen: %d times",prices[j],priceCount.get(prices[j]));
            price += "\n-------------\t\t-------------\n";

        }
        return price;
    }

    public void duplicateItems(String name, String price){
        HashMap<String, Integer> priceCount;
        for (GroceryItem g : groceryItems) {
            if (g.getName().equals(name)) {
                priceCount = g.getPriceCount();
                if(priceCount.containsKey(price)){
                    priceCount.put(price, priceCount.get(price)+1);
                }
                else{
                    priceCount.put(price, 1);
                }
            }
        }
    }

    public ArrayList<GroceryItem> getGroceryItems() {
        return groceryItems;
    }
}
