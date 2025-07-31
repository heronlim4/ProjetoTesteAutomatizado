const fs = require('fs');
const path = require('path');
const { parseString } = require('xml2js');

// Diretórios de entrada/saída
const INPUT_DIR = path.join(__dirname, '../target/surefire-reports');
const OUTPUT_FILE = path.join(__dirname, 'reports/test-results.json');

function processReports() {
  const reports = [];

  // 1. Ler todos os arquivos XML de relatório
  fs.readdirSync(INPUT_DIR).forEach(file => {
    if (file.endsWith('.xml')) {
      const xml = fs.readFileSync(path.join(INPUT_DIR, file), 'utf-8');

      parseString(xml, (err, result) => {
        if (err) return;

        // 2. Estruturar dados
        const suite = {
          name: result.testsuite.$.name,
          tests: parseInt(result.testsuite.$.tests),
          failures: parseInt(result.testsuite.$.failures),
          time: parseFloat(result.testsuite.$.time),
          testCases: []
        };

        // 3. Processar casos de teste
        result.testsuite.testcase.forEach(tc => {
          suite.testCases.push({
            name: tc.$.name,
            className: tc.$.classname,
            time: parseFloat(tc.$.time),
            status: tc.failure ? 'failed' : 'passed',
            failureMessage: tc.failure ? tc.failure[0]._ : null
          });
        });

        reports.push(suite);
      });
    }
  });

  // 4. Gerar relatório consolidado
  const summary = {
    timestamp: new Date().toISOString(),
    totalTests: reports.reduce((sum, suite) => sum + suite.tests, 0),
    totalFailures: reports.reduce((sum, suite) => sum + suite.failures, 0),
    totalTime: reports.reduce((sum, suite) => sum + suite.time, 0),
    suites: reports
  };

  // 5. Salvar em JSON
  fs.writeFileSync(OUTPUT_FILE, JSON.stringify(summary, null, 2));
  console.log(`Relatório JSON gerado em: ${OUTPUT_FILE}`);
}

processReports();