package blair.carina.HurtLocker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseData {

    private String output = "";
    private ArrayList<GroceryItem> groceryItems = new ArrayList<>();
    private ArrayList<String> foodNames = new ArrayList<>();


    public String outputText(String str){
        parseInfo(str);
        for (GroceryItem groceryItem : groceryItems) {
            String name = formatNamePrint(groceryItem);
            String price = formatPricePrint(groceryItem);
            String total = formatTotalItems(groceryItem);
            output += name + total + "\n=============\t\t=============\n"+ price +"\n";
        }
        return output;

    }

    private void parseInfo(String str){
        String parse = "((?i)\\bname:\\b)(\\w+)([;@^*%!])(\\b(?i)price:\\b)(\\d.\\d{2})([;@^*%!])((?i)\\btype:\\b)(\\w+)([;@^*%!])(\\b(?i)expiration:\\b)(\\d/\\d{2}/\\d{4})(##)";
        Pattern pattern = Pattern.compile(parse);
        Matcher matcher = pattern.matcher(str);
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

    private String formatName(String name){
        name = name.replaceAll("\\d+","o");
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    private String formatTotalItems(GroceryItem groceryItem){
        HashMap<String, Integer> priceCount;
        int total = 0;
        priceCount = groceryItem.getPriceCount();
        Object[] prices = priceCount.keySet().toArray();
        for (int j = 0; j < priceCount.size(); j++){
            total += priceCount.get(prices[j]);
        }

        return String.format("seen: %d times", total);
    }

    private String formatNamePrint(GroceryItem groceryItem){
        return String.format("name:\t%s\t\t", groceryItem.getName());
    }

    private String formatPricePrint(GroceryItem groceryItem){
        HashMap<String, Integer> priceCount;
        String price = "";
        priceCount = groceryItem.getPriceCount();
        Object[] prices = priceCount.keySet().toArray();
        for (int j = 0; j < priceCount.size(); j++){
            price += String.format("Price:\t%s\t\tseen: %d times\n" +
                    "-------------\t\t-------------\n",prices[j], priceCount.get(prices[j]));
        }
        return price;
    }

    private void duplicateItems(String name, String price){
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




    public class MissingValueException extends Exception{}
}
