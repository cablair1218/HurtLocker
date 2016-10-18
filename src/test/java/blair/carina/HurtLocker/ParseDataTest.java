package blair.carina.HurtLocker;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParseDataTest {
    ParseData parser;
    String str = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016";

    @Before
    public void initialize(){parser = new ParseData();}

    @Test
    public void formatTotalItemsTest(){
        parser.jerkSonParse(str);
        Assert.assertEquals("Should return seen: 1times", "seen: 1 times", parser.formatTotalItems(parser.getGroceryItems().get(0)) );
    }

    @Test
    public void formatNamePrintTest(){
        parser.jerkSonParse(str);
        Assert.assertEquals("Should return name: Milk", "name:\tMilk\t\t", parser.formatNamePrint(parser.getGroceryItems().get(0)) );
    }

    @Test
    public void formatPricePrintTest(){
        parser.jerkSonParse(str);
        Assert.assertEquals("Should return Price: 3.23", "Price:\t3.23\t\tseen: 1 times\n-------------\t\t-------------\n", parser.formatPriceCount(parser.getGroceryItems().get(0)));
    }

    @Test
    public void getGroceryListTest(){
        parser.jerkSonParse(str);
        Assert.assertEquals("Should return 2", 2, parser.getGroceryItems().size());
    }

}
