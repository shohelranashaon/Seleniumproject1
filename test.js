import { Browser, Builder,By,Key, } from "selenium-webdriver";
const driver = new Builder().forBrowser(Browser.CHROME).build();

async function testRunner(){
        await driver.manage().window().maximize(); 
        await driver.get("https://ngof.4axizerp.com/login");
        await driver.sleep(3000);

        await driver.findElement(By.id("email")).sendKeys("admin@ngof.org");
        await driver.sleep(2000);
        await driver.findElement(By.id("password")).sendKeys("12345678")

        await driver.findElement(By.className("btn btn-primary btn-block mt-2 login_button")).click();

        await driver.findElement(By.xpath("//a[normalize-space()='HRM']")).click();
        await driver.sleep(3000);

        await driver.findElement(By.xpath("//a[normalize-space()='Employee']")).click();

}

testRunner();