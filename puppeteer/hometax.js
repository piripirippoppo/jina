'use strict'
const puppeteer = require('puppeteer');
const moment = require('moment');
const comnFIleControl = require('./comn/comnFIleControl');

require('dotenv').config();
const _screenshotPath = process.env.SCEENSHOT_PATH;

(async () => {
 
    comnFIleControl.initDirectory();

    //initial puppeteer
    const tempBrowser = await puppeteer.launch({ 
        headless: false
        , executablePath: process.env.EXECUTABLE_PATH
    });

    await hometax();

    tempBrowser.close();
})()
.catch(err => console.error(err))
.finally(()=> console.log('finally'));

const hometax = async () => {
    const browser = await puppeteer.launch({ headless: false, args :[`--window-size=1920, 1080`], slowMo: 30 });
    const page = await browser.newPage();
    console.log("Start HomeTax");

    await page.goto('https://www.hometax.go.kr/websquare/websquare_cdn.html?w2xPath=/ui/pp/index.xml');

    // Set screen size
    await page.setViewport({ width: 1080, height: 1024 });
    sleep(7000);

    let login = await getxPathEleOne("//a[@id='group88615548']", page);

    await Promise.all([
        await login.click(), 
        await page.waitForNavigation()
    ])

    let xiframe = "//iframe[@id='txppIframe']";
    await page.waitForXPath(xiframe);
    const iframe = await page.$("iframe[name='txppIframe']")
    const contentFrame = await iframe.contentFrame();

    let tabIDPWD = await getxPathEleOne("//a[@id='anchor15']", contentFrame);

    sleep(1000);
    await tabIDPWD.click();

    sleep(1000);
    await Promise.all([
        await contentFrame.type("#iptUserId", process.env.HOMETAX_ID)
        , await contentFrame.type("#iptUserPw", process.env.HOMETAX_PWD)
        , await contentFrame.click("#anchor25")
        , await page.waitForNavigation()        // X
    ])

    let deClareVATIcon = await getxPathEleOne("//span[@id='myMenuQuickMenuNm_5']", page);
    
    await Promise.all([
        await screenCapture("after-login", page) 
        , await deClareVATIcon.click()
        , await page.waitForNavigation()        // X
    ])

    sleep(7000);
    
    console.log("END HomeTax");
    await browser.close();
}

const getxPathEleOne = async (inPath, inPage) => {
    let xTarget = inPath;
    await inPage.waitForXPath(xTarget);
    let xTagetEle = await inPage.$x(xTarget);
    return xTagetEle[0];
}

const screenCapture = async (inFileName, inPage) => {
    // let fileName =_screenshotPath + (++count).toString().padStart(3,"0") + "_" + inFileName + ".png"
    let fileName =_screenshotPath + inFileName + "_" + getTimestampString() + ".png"
    await inPage.screenshot({path : fileName});
}

const getTimestampString = () => {
    let dt = new Date();
    return moment(dt).format("YYYYMMDDHHmmss");
}

const sleep = (ms) => {
    const wakeUptime = Date.now() + ms;
    while (Date.now() < wakeUptime){};
}
