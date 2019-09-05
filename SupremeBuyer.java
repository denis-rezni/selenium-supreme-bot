import java.util.ArrayList;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SupremeBuyerMain {
    static long start_filler = 0;
    static boolean firstBuyer = true;
    static String fakeLink = "https://www.supremenewyork.com/shop/pants/v0uvqi47z/njdvcn09m?alt=0";
    static boolean buyingMode = false;
    static Scanner in = new Scanner(System.in);

    static class Man {
        String name;
        String billingName;
        String mail;
        String phone;
        String address;
        String city;
        String zip;

        static Man FAKE_ONE = new Man("Buyer Name", "someMail@gmail.com", "+79065583265", "Stahanovtsev 10, 10",
                "Saint-Petersburg", "125352", "FAKE_ONE");
        static Man FAKE_TWO = new Man("Yuri Golovin", "whatisthis@gmail.com", "+79063672954", "Ligovskii 5",
                "Saint-Petersburg", "196325", "FAKE_TWO");
        static Man FAKE_THREE = new Man("Max Polskii", "someMail@gmail.com", "+79062093460", "Nevskii 152",
                "Saint-Petersburg", "121516", "FAKE_THREE");

        static ArrayList<Man> menGetter() {
            ArrayList<Man> list = new ArrayList<Man>();
            list.add(FAKE_ONE);
            list.add(FAKE_TWO);
            list.add(FAKE_THREE);
            return list;
        }

        Man(String billingName, String mail, String phone, String address, String city, String zip, String name) {
            this.billingName = billingName;
            this.mail = mail;
            this.phone = phone;
            this.address = address;
            this.city = city;
            this.zip = zip;
            this.name = name;
        }

        public Man(int number) {
            ArrayList<Man> list = menGetter();
            this.billingName = list.get(number - 1).billingName;
            this.mail = list.get(number - 1).mail;
            this.phone = list.get(number - 1).phone;
            this.address = list.get(number - 1).address;
            this.city = list.get(number - 1).city;
            this.zip = list.get(number - 1).zip;
        }

    }

    static class Card {
        String name;
        String num;
        String month;
        String year;
        String vval;
        String cardType;

        static Card FAKE_MASTER = new Card("3456121256569898", "10", "2020", "566", "master", "FAKE_MASTER");
        static Card FAKE_VISA = new Card("0987135662893412", "05", "2019", "239", "visa", "FAKE_VISA");

        Card(String num, String month, String year, String vval, String cardType, String name) {
            this.num = num;
            this.month = month;
            this.year = year;
            this.vval = vval;
            this.cardType = cardType;
            this.name = name;
        }

        static ArrayList<Card> cardsGetter() {
            ArrayList<Card> list = new ArrayList<Card>();
            list.add(FAKE_MASTER);
            list.add(FAKE_VISA);
            return list;
        }

        public Card(int number) {
            ArrayList<Card> list = cardsGetter();
            this.num = list.get(number - 1).num;
            this.month = list.get(number - 1).month;
            this.year = list.get(number - 1).year;
            this.vval = list.get(number - 1).vval;
            this.cardType = list.get(number - 1).cardType;

        }

        public void setValue(int n) {
            ArrayList<Card> list = cardsGetter();
            this.name = list.get(n - 1).name;
            this.num = list.get(n - 1).num;
            this.month = list.get(n - 1).month;
            this.year = list.get(n - 1).year;
            this.vval = list.get(n - 1).vval;
            this.cardType = list.get(n - 1).cardType;
        }

    }

    static void extension(WebDriver driver) throws InterruptedException {
        driver.get("https://chrome.google.com/webstore/detail/frigate3-proxy-helper/hdbipekpdpggjaipompnomhccfemaljm");
        Thread.sleep(2000);
        Actions actions = new Actions(driver);

        actions.sendKeys(Keys.TAB).build().perform();
        actions.sendKeys(Keys.TAB).build().perform();
        actions.sendKeys(Keys.TAB).build().perform();
        actions.sendKeys(Keys.TAB).build().perform();
        actions.sendKeys(Keys.TAB).build().perform();
        actions.sendKeys(Keys.ENTER).build().perform();
        in.next();
    }

    public static String cleaning(String string) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == ' ' || c == '/' || Character.isDigit(c)
                    || c == '-') {
                s.append(c);
            }
        }
        return s.toString().toLowerCase();
    }

    public static String sizeGetter(WebDriver driver, String size) {
        ArrayList<WebElement> classedElements = (ArrayList<WebElement>) driver.findElements(By.xpath("//option"));
        String result = "";

        for (int i = 0; i < classedElements.size(); i++) {
            String current = classedElements.get(i).getText();
            if (current.contains(size)) {
                result = current + "";
                return result;
            }
        }
        return result;
    }

    public static ArrayList<WebElement> findInBig(String name, WebDriver driver, WebElement bigElement) {
        ArrayList<WebElement> result = new ArrayList<WebElement>();
        ArrayList<WebElement> classedElements = (ArrayList<WebElement>) bigElement
                .findElements(By.className("name-link"));
        WebElement element = null;
        for (int i = 0; i < classedElements.size(); i++) {
            element = classedElements.get(i);
            String clean = cleaning(element.getText());
            if (clean.contains(name)) {
                result.add(classedElements.get(i));
            }
        }
        return result;

    }

    public static ArrayList<WebElement> findInAll(String name, WebDriver driver) {
        ArrayList<WebElement> allElements = (ArrayList<WebElement>) driver.findElements(By.className("inner-article"));
        ArrayList<WebElement> result = new ArrayList<WebElement>();
        ArrayList<WebElement> classedElements = (ArrayList<WebElement>) driver.findElements(By.className("name-link"));
        WebElement element = null;
        boolean foundFirst = false;
        for (int i = 0; i < classedElements.size(); i += 2) {
            element = classedElements.get(i);
            String clean = cleaning(element.getText());
            if (clean.contains(name)) {
                result.add(allElements.get(i / 2));
                foundFirst = true;
            } else if (foundFirst) {
                return result;
            }
        }
        return result;

    }

    static long buyer(String name, String size, String type, String color, WebDriver driver)
            throws InterruptedException {
        if (!firstBuyer) {
        new WebDriverWait(driver, 10)
        .until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("checkout
        now")));
        }

        driver.get("https://www.supremenewyork.com/shop/all/" + type);
        boolean noexist = true;
        if (firstBuyer) {
            Thread.sleep(in.nextLong());
        }
        ArrayList<WebElement> suitable = new ArrayList<WebElement>();
        while (noexist) {
            long beforeFind = System.currentTimeMillis();
            suitable = findInAll(name, driver);
            System.out.println(System.currentTimeMillis() - beforeFind + " findInAll");
            if (!suitable.isEmpty()) {
                noexist = false;
            } else {
                if (firstBuyer) {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    driver.get("http://www.supremenewyork.com/shop/all/" + type);
                    // driver.navigate().refresh();
                } else {
                    return 10;
                }
            }
        }
        firstBuyer = false;
        System.out.println("There's one...");
        long start = System.currentTimeMillis();

        WebElement element = null;
        boolean hasSelected = false;
        if (!color.equals("unknown")) {
            for (int i = 0; i < suitable.size(); i++) {
                if (!(findInBig(color, driver, suitable.get(i)).isEmpty())) {
                    if (!(findInBig(name, driver, suitable.get(i)).isEmpty())) {
                        element = findInBig(name, driver, suitable.get(i)).get(0);
                        hasSelected = true;
                        break;
                    }
                }
            }

        }
        if (!hasSelected) {
            ArrayList<WebElement> allelements = findInAll(name, driver);
            element = allelements.get(0);
        }
        long counter1 = System.currentTimeMillis();
        System.out.println(counter1 - start + " found the right color");
        element.click();

        try {
            new WebDriverWait(driver, 4).until(ExpectedConditions.presenceOfElementLocated(By.name("commit")));
        } catch (Exception e2) {
            return 0;
        }
        if (!(size.equals("onesize"))) {
            if (!type.equals("shoes")) {
                new Select(driver.findElement(By.name("size"))).selectByVisibleText(size);
            } else {
                try {
                    new Select(driver.findElement(By.name("size"))).selectByVisibleText(sizeGetter(driver, size));
                } catch (Exception e) {
                    return 100000;
                }

            }
        }
        try {
            WebElement selectbutton = driver.findElement(By.name("commit"));
            selectbutton.click();
            long counter2 = System.currentTimeMillis();
            Thread.sleep(200);
            System.out.println(counter2 - counter1 + " pressed on the item, chose size, pressed select");
            boolean gotTheItem = false;
            while (!gotTheItem) {
                gotTheItem = true;
                try {
                    new WebDriverWait(driver, 2)
                            .until(ExpectedConditions.presenceOfElementLocated(By.id("cart-remove")));
                    long counter3 = System.currentTimeMillis();
                    System.out.println(counter3 - counter2 + " waited for cart-remove");
                    Thread.sleep((counter3 - counter2 >= 500) ? 0 : 500 - (counter3 - counter2));
                } catch (Exception e1) {
                    gotTheItem = false;
                    driver.findElement(By.name("commit")).click();
                }
            }

        } catch (Exception e) {
            return 10000;
        }
        return start;

    }

    static long smartFiller(WebDriver driver, Card card) throws InterruptedException {
        long counter1 = System.currentTimeMillis();
        driver.get("https://www.supremenewyork.com/checkout");
        // new WebDriverWait(driver, 10)
        // .until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("checkout
        // now")));
        // driver.findElement(By.partialLinkText("checkout now")).click();
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("order[email]")));
        } catch (Exception e) {
            new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("checkout now")));
            driver.findElement(By.partialLinkText("checkout now")).click();

        }
        start_filler = System.currentTimeMillis();
        // driver.get("https://www.supremenewyork.com/checkout");

        // pack for me
        long counter2 = System.currentTimeMillis();
        System.out.println(counter2 - counter1 + " pressed checkout and waited for order_billing_name");
        if (!card.cardType.equals("visa")) {
            new Select(driver.findElement(By.xpath("//*[@id='credit_card_type']"))).selectByValue(card.cardType);
        }
        WebElement cardnumber = driver.findElement(By.xpath("//*[@placeholder='number']"));

        cardnumber.clear();
        String num = card.num;
        Actions actions = new Actions(driver);
        for (int i = 0; i < num.length(); i++) {
            // cardnumber.sendKeys(num.charAt(i) + "");
            actions.sendKeys(num.charAt(i) + "").build().perform();
        }
        new Select(driver.findElement(By.xpath("//*[@id='credit_card_month']"))).selectByValue(card.month);
        new Select(driver.findElement(By.xpath("//*[@id='credit_card_year']"))).selectByValue(card.year);
        driver.findElement(By.xpath("//*[@id='vval']")).sendKeys(card.vval);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@name='order[terms]']")));

        WebElement tobuy = driver.findElement(By.xpath("//*[@name='commit']"));
        if (buyingMode) {
            System.out.println("entered if(buyingMode) ");
            System.out.println("bought shit know im sayin");
            tobuy.click();
        }
        System.out.println(System.currentTimeMillis() - counter2 + " filled all the shit left");
        long finish = System.currentTimeMillis();
        return finish;
    }

    public static void addressSaver(WebDriver driver, String link, Man man) throws InterruptedException {
        driver.get(link);
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("commit")));
        driver.findElement(By.name("size")).click();
        Actions actions = new Actions(driver);
        for (int i = 0; i < 12; i++) {
            actions.sendKeys(Keys.ARROW_DOWN).build().perform();
        }
        actions.sendKeys(Keys.ENTER).build().perform();
        try {
            WebElement selectbutton = driver.findElement(By.name("commit"));
            selectbutton.click();

        } catch (Exception e) {
        }
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("checkout now")));
        driver.findElement(By.partialLinkText("checkout now")).click();
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='order_email']")));
        driver.findElement(By.xpath("//*[@id='order_billing_name']")).sendKeys(man.billingName);
        driver.findElement(By.xpath("//*[@id='order_email']")).sendKeys(man.mail);
        driver.findElement(By.xpath("//*[@id='order_tel']")).sendKeys(man.phone);
        driver.findElement(By.xpath("//*[@id='bo']")).sendKeys(man.address);
        driver.findElement(By.xpath("//*[@id='order_billing_city']")).sendKeys(man.city);
        driver.findElement(By.xpath("//*[@id='order_billing_zip']")).sendKeys(man.zip);
        new Select(driver.findElement(By.xpath("//*[@id='order_billing_country']"))).selectByValue("RU");
        driver.findElement(By.xpath("//*[@id='store-address']")).click();

        WebElement cardnumber = driver.findElement(By.xpath("//*[@placeholder='number']"));

        cardnumber.clear();
        String num = Card.FAKE_VISA.num;// fake

        for (int i = 0; i < num.length(); i++) {
            actions.sendKeys(num.charAt(i) + "").build().perform();;
        }
        new Select(driver.findElement(By.xpath("//*[@id='credit_card_month']"))).selectByValue(Card.FAKE_VISA.month);
        new Select(driver.findElement(By.xpath("//*[@id='credit_card_year']"))).selectByValue(Card.FAKE_VISA.year);
        driver.findElement(By.xpath("//*[@id='vval']")).sendKeys(Card.FAKE_VISA.vval);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@name='order[terms]']")));

        WebElement tobuy = driver.findElement(By.xpath("//*[@name='commit']"));

        tobuy.click();

        in.next();
        driver.get(link);

        while (!driver.findElements(By.partialLinkText("checkout now")).isEmpty()) {
            driver.findElement(By.name("commit")).click();
            driver.navigate().refresh();
            Thread.sleep(2000);
        }

    }

    public static WebDriver bot(Card card) throws InterruptedException {
        System.out.println("Hello, Denis. I'm ready to cop you some Supreme!");
        System.out.println("Link : *.supremenewyork.com");
        System.out.println("If you want to buy, enter password, else type something (not password)");
        if (in.next().equals("harvest")) {
            buyingMode = true;
            System.out.println("Buying mode activated");
            System.out.println("List of people : ");
            for (int i = 0; i < Man.menGetter().size(); i++) {
                // if (!Man.menGetter().get(i).name.contains("FAKE")) {
                System.out.println((i + 1) + " " + Man.menGetter().get(i).name);
                // }
            }
            System.out.println("Enter a number, starting from 1");
            int n = in.nextInt();
            Man man = new Man(n);
            System.out.println("List of cards : ");
            for (int i = 0; i < Card.cardsGetter().size(); i++) {
                // if (!Card.cardsGetter().get(i).name.contains("FAKE")) {
                System.out.println((i + 1) + " " + Card.cardsGetter().get(i).name);
                // }
            }
            System.out.println("Enter a number, starting from 1");
            n = in.nextInt();
            card.setValue(n);
            WebDriver driver = new ChromeDriver();
            addressSaver(driver, fakeLink, man);
            return driver;
        } else {
            System.out.println("Testing mode activated");
            System.out.println("Here's the list of people you can test on:");
            for (int i = 0; i < Man.menGetter().size(); i++) {
                System.out.println((i + 1) + " " + Man.menGetter().get(i).name);
            }
            System.out.println("Enter a number, starting from 1");
            int n = in.nextInt();
            Man man = new Man(n);
            System.out.println("List of cards you can test on: ");
            for (int i = 0; i < Card.cardsGetter().size(); i++) {
                System.out.println((i + 1) + " " + Card.cardsGetter().get(i).name);
            }
            System.out.println("Enter a number, starting from 1");
            n = in.nextInt();
            card.setValue(n);
            System.out.println("Good choice! Starting the bot...");
            WebDriver driver = new ChromeDriver();
            addressSaver(driver, fakeLink, man);
            return driver;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\denis\\Documents\\programming\\selenium webdriver\\webdrivers\\chromedriver.exe");
        Card card = Card.FAKE_MASTER;
        WebDriver driver = bot(card);
        long start = buyer("pinhole", "Large", "shirts", "lime", driver);//all lowercase, size is Small/Medium/Large/XLarge
        long finish = smartFiller(driver, card);
        System.out.println("program has been working for " + (finish - start) + " millis");
        System.out.println("filler has been working for " + (finish - start_filler) + " millis");

        in.close();
        
    }
    // actual trash link
    // "https://www.supremenewyork.com/shop/pants/v0uvqi47z/njdvcn09m?alt=0"
    
}   
