'use strict'
const puppeteer = require('puppeteer');
const comnFunc = require('./comn/comnFunc');

const startBss = async () => {
    const browser = await puppeteer.launch({
        headless: process.env.HEADLESS == "0" ? false : true 
        , args :[`--window-size=1920, 1080`]
        , slowMo: 30
        , executablePath: process.env.EXECUTABLE_PATH
     });
    const page = await browser.newPage();
    console.log("Start BirchStreet Automation of work!");

    // Set screen size
    await page.setViewport({ width: 1920, height: 1080 });

    await page.goto(process.env.BSS_URL_NSSO, {waitUntil: 'networkidle0'});
    
    // console.log("current page count ", (await browser.pages()).length); // 3

    // 1. Login to Dream Tower Integrated Resort Marketplace intro
    console.log("1. Login to Dream Tower Integrated Resort Marketplace intro");
    let target = await comnFunc.getxPathEleOne("//font[@class='NavStyle0']", page);
    await Promise.all([await target.click(),await page.waitForNavigation()]);
    
    // 2. login (input ID, input PWD then click loging button )
    console.log("2. login (input ID, input PWD then click loging button )");
    let btnLogin = await comnFunc.getxPathEleOne("//input[@id='submitLogin']", page);
    comnFunc.sleep(1000);
    await page.type("#loginID", process.env.BSS_ID);
    await page.type("#password", process.env.BSS_PWD);
    await Promise.all([await btnLogin.click(), await page.waitForNavigation()]);
    
    // After Login Successfuly ScreenCapture 
    console.log("After Login Successfuly ScreenCapture ");
    await comnFunc.screenCapture("1_after-login", page, process.env.SCEENSHOT_PATH);


    //3. main menu select in frame
    console.log("3. main menu select in frame");
    let xframe = "//frame[@id='NavList']";
    await page.waitForXPath(xframe);
    const Navframe = await page.$("frame[name='NavList']");
    const NavContentFrame = await Navframe.contentFrame();
    
    // 4. Main Menu > Administration
    console.log("4. Main Menu > Administration");
    target = await comnFunc.getxPathEleOne("//div[@id='HN40']", NavContentFrame);
    await target.click();

    //5. Main Menu > Administration > Company Level Maintenance
    console.log("5. Main Menu > Administration > Company Level Maintenance");
    target = await comnFunc.getxPathEleOne("//div[@id='HN41']", NavContentFrame);
    await target.click();
    
    //6. Main Menu > Administration > Company Level Maintenance > Person
    console.log("6. Main Menu > Administration > Company Level Maintenance > Person");
    target = await comnFunc.getxPathEleOne("//div[@id='HN43']", NavContentFrame);
    await target.click();
 
    // find Child Popup 
    const newPagePromise = new Promise(x => browser.once('targetcreated', target => x(target.page()))); 
    const popup = await newPagePromise;

    // Swich popup Frames and elements
    let xFrameNavList = "//frame[@id='NavList']";
    await popup.waitForXPath(xFrameNavList);
    let xFrameEntryScreen = "//frame[@id='EntryScreen']";
    await popup.waitForXPath(xFrameEntryScreen);

    // wait for frames generate
    const NavframePopup = await popup.$("#NavList");
    const NavframePopupEntry = await popup.$("#EntryScreen");
    const NavContentFramePopup = await NavframePopup.contentFrame();

    comnFunc.sleep(5000);

    //7, 8, 9. Choice Search Clumn "loging name"
    
    await Promise.all([
        await searchUserByOption('LOGINNAME', "123132", NavContentFramePopup)
        ,comnFunc.sleep(3000)
    ]);

    // After Check Search user ScreenCapture 
    console.log("After Check Search user ScreenCapture ");
    await comnFunc.screenCapture("2_After-Search-Person", popup, process.env.SCEENSHOT_PATH);

    // get find user HTML table to ArrayList Data
    let pesrsonNavTableArr = await NavContentFramePopup.evaluate(
        () => Array.from(
            document.querySelectorAll('table[id="NavTable"] > tbody > tr'),
            row => Array.from(row.querySelectorAll('th, td'), cell => cell.innerText)
        )
    );
    console.log(pesrsonNavTableArr);
    
    //10. table first row selecting
    console.log("10. table first row selecting");
    if (pesrsonNavTableArr.length > 0){
        await NavContentFramePopup.evaluate(() => {
            let tableRow = document.querySelector('#NavListDiv > table > tbody > tr:nth-child(1)');
            tableRow.click();
        });
        
        // let tableRow = await comnFunc.getxPathEleOne("//tr[@id='tb1']", NavContentFramePopup);       // X
        // await tableRow.click();      // X
    }
    
    //await NavContentFramePopup.focus('#SearchValue');       // O
    // let el = await NavContentFramePopup.waitForSelector('#NavListDiv > table > tbody > tr:nth-child(1)', { visible: true })  // X
    // await el.click('#NavListDiv > table > tbody > tr:nth-child(1)');             // X
    // await NavContentFramePopup.focus("#NavListDiv > table > tbody > tr:nth-child(1)");           // X

    // let el = await NavContentFramePopup.waitForSelector('#NavListDiv > table > tbody > tr:nth-child(1)');       // X
    // await el.click();           // X
    
    // NavContentFramePopup.waitForXPath("//tr[@id='tb1']").then(selector => selector.click()) // X

    // NavContentFramePopup.hover('#NavListDiv > table > tbody > tr:nth-child(1)');    // X
    // NavContentFramePopup.click('#NavListDiv > table > tbody > tr:nth-child(1)');    // X


    comnFunc.sleep(5000);

    // After Check Selection row ScreenCapture 
    console.log("After Check Selection row");
    await comnFunc.screenCapture("3_After-Selection-Row", popup, process.env.SCEENSHOT_PATH);

    // 11. Deactivate this person Checked
    console.log("11. Deactivate this person Checked");
    const NavEntryContentFramePopup = await NavframePopupEntry.contentFrame();
    let xFrameEntry = "//frame[@id='Entry']";
    await NavEntryContentFramePopup.waitForXPath(xFrameEntry);
    const NavframePopup2 = await NavEntryContentFramePopup.$("#Entry");
    const NavContentFramePopup2 = await NavframePopup2.contentFrame();

    let deActiveCheck = await comnFunc.getxPathEleOne("//input[@id='FIELD4']", NavContentFramePopup2);
    await deActiveCheck.click();

    //12. button click "Save" 
    console.log(`12. button click "Save" `);
    let btnSave = await comnFunc.getxPathEleOne("//button[@id='SaveRecPanelToolTab']", NavContentFramePopup2);
    await btnSave.click();

    // After Check Search user ScreenCapture 
    console.log("After Check Save user ScreenCapture ");
    await comnFunc.screenCapture("4_After-Save-Person", popup, process.env.SCEENSHOT_PATH);

    //13. check complete status and email sendor
    console.log("13. check complete status and email sendor");
    // todo somting add varify action 

    console.log("End BirchStreet Automation of work!");
    comnFunc.sleep(3000);
    await browser.close();
}

const searchUserByOption = async (inOption, inSearchText, inFrame) => {
    //7. Choice Search Clumn "loging name" or other Option value
    console.log(`7. Choice Search Clumn "loging name" or other Option value`);
    await inFrame.select('#SearchField', inOption);
    comnFunc.sleep(1000);
    
    //8. input Search Text "loging name"
    console.log(`8. input Search Text "loging name"`);
    await inFrame.type("#SearchValue", inSearchText);
    comnFunc.sleep(1000);

    //9. Click "GO" to Search User
    console.log(`9. Click "GO" to Search User`);
    let btnGo = await comnFunc.getxPathEleOne("//a[@id='searchButton']", inFrame);
    await btnGo.click();
    comnFunc.sleep(1000);
}



module.exports = {
    startBss
}