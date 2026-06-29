const { chromium } = require('playwright');
const outDir = process.argv[2];
(async () => {
  const browser = await chromium.launch();
  const page = await browser.newPage();
  await page.setViewportSize({ width: 1280, height: 800 });
  const stories = [
    ['single',   'components-importantnotice--single-notice'],
    ['multiple', 'components-importantnotice--multiple-notices'],
    ['empty',    'components-importantnotice--empty-state'],
    ['custom',   'components-importantnotice--custom-colors'],
  ];
  for (const [name, id] of stories) {
    await page.goto('http://localhost:6006/?path=/story/' + id, { waitUntil: 'networkidle', timeout: 20000 });
    await page.waitForTimeout(2500);
    await page.screenshot({ path: outDir + '\\' + name + '.png' });
    console.log('captured: ' + name);
  }
  await browser.close();
})();
