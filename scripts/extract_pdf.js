const fs = require('fs');
const path = require('path');
const { PDFParse } = require('pdf-parse');

const pdfPath = path.join(__dirname, '..', 'constructrack total.pdf');
const outPath = path.join(__dirname, '..', 'constructrack_total.txt');

async function extract() {
  try {
    const dataBuffer = fs.readFileSync(pdfPath);
    const parser = new PDFParse({ data: dataBuffer });
    const result = await parser.getText();
    fs.writeFileSync(outPath, result.text, { encoding: 'utf8' });
    await parser.destroy();
    console.log('Texto extraído a', outPath);
  } catch (err) {
    console.error('Error extrayendo PDF:', err.message);
    process.exitCode = 2;
  }
}

extract();
