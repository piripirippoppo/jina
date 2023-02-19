'use strict'
const fs = require('fs');
const initScreenshot = './screenshot';

const initDirectory = () => {

    console.log(initScreenshot);

    if (!fs.existsSync(initScreenshot)){
        fs.mkdirSync(initScreenshot);
    }
}

module.exports = {
    initDirectory
}


