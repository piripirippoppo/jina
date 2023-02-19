'use strict'
const puppeteer = require('puppeteer');
const comnFIleControl = require('./comn/comnFIleControl');
const bssControll = require('./bss');
const bssqaControll = require('./bss_qa');
const bssqagroupControll = require('./bss_qa_group');

require('dotenv').config();

(async () => {
    comnFIleControl.initDirectory(process.env.SCREENSHOT_PATH);

    //initial puppeteer
    const tempBrowser = await puppeteer.launch({ headless: process.env.HEADLESS == "0" ? false : true , executablePath: process.env.EXECUTABLE_PATH});

    if (process.env.MODE == "PROD"){
        await bssControll.startBss();
    }else if(process.env.MODE == "DEV"){
        await bssqaControll.startBss();
    } else {
        await bssqagroupControll.startBss();
    }

    tempBrowser.close();
})()
.catch(err => console.error(err))
.finally(()=> console.log('finally'));
