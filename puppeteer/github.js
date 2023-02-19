'use strict'
const puppeteer = require('puppeteer');
const comnFIleControl = require('./comn/comnFIleControl');

require('dotenv').config();

(async () => {
    comnFIleControl.initDirectory();
    //initial puppeteer
    const tempBrowser = await puppeteer.launch({ 
        headless: false
        , executablePath: process.env.EXECUTABLE_PATH
    });

    await githublogin();
    tempBrowser.close();
})()
.catch(err => console.error(err))
.finally(()=> console.log('finally'));

const githublogin = async () => {
    const browser = await puppeteer.launch({ headless: false });
    const page = await browser.newPage();

    console.log("Start githublogin");

    let screenshot = "screenshot.png";

    await page.goto('https://github.com/login')
    await page.type('#login_field', process.env.GITHUB_USER)
    await page.type('#password', process.env.GITHUB_PWD)
    await page.click('[name="commit"]')

    await page.waitForNavigation()

    await page.waitForSelector('.wb-break-word');

    await page.screenshot({ path: screenshot })
    browser.close()
    console.log('See screenshot: ' + screenshot)
}

