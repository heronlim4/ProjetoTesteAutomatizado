document.addEventListener('DOMContentLoaded', () => {
    const runTestsBtn = document.getElementById('runTestsBtn');
    const resultsBody = document.querySelector('#resultsTable tbody');

    // Carrega resultados anteriores
    loadTestResults();

    runTestsBtn.addEventListener('click', async () => {
        runTestsBtn.disabled = true;
        runTestsBtn.textContent = 'Executando...';

        try {
            // Dispara workflow via GitHub API
            await triggerTests();

            // Monitora execução
            monitorTestExecution();
        } catch (error) {
            console.error('Erro:', error);
            runTestsBtn.disabled = false;
            runTestsBtn.textContent = '?? Executar Todos os Testes';
        }
    });
});

async function triggerTests() {
    const response = await fetch(
        'https://api.github.com/repos/seu-usuario/seu-repositorio/actions/workflows/tests.yml/dispatches',
        {
            method: 'POST',
            headers: {
                'Authorization': 'token SEU_PERSONAL_ACCESS_TOKEN',
                'Accept': 'application/vnd.github.v3+json'
            },
            body: JSON.stringify({
                ref: 'main',
                inputs: {
                    tags: getSelectedTags()
                }
            })
        }
    );

    if (!response.ok) {
        throw new Error('Falha ao iniciar testes');
    }
}

function getSelectedTags() {
    return Array.from(document.querySelectorAll('.tag-filter:checked'))
        .map(checkbox => checkbox.value)
        .join(',');
}

async function monitorTestExecution() {
    const interval = setInterval(async () => {
        const status = await getWorkflowStatus();

        if (status === 'completed') {
            clearInterval(interval);
            loadTestResults();
            document.getElementById('runTestsBtn').disabled = false;
            document.getElementById('runTestsBtn').textContent = '?? Executar Todos os Testes';
        }
    }, 10000);
}

async function getWorkflowStatus() {
    // Implementar lógica para verificar status do workflow
    // Retorna: 'running' | 'completed'
    return 'completed'; // Exemplo simplificado
}

async function loadTestResults() {
    try {
        const response = await fetch('reports/test-results.json');
        const results = await response.json();

        updateDashboard(results);
    } catch (error) {
        console.error('Erro ao carregar resultados:', error);
    }
}

function updateDashboard(data) {
    const resultsBody = document.querySelector('#resultsTable tbody');
    resultsBody.innerHTML = '';

    let passed = 0;
    let failed = 0;

    data.testResults.forEach(suite => {
        suite.testResults.forEach(test => {
            const row = document.createElement('tr');
            row.classList.add(test.status === 'passed' ? 'passed' : 'failed');

            row.innerHTML = `
                <td>${test.fullName}</td>
                <td>${test.status === 'passed' ? '? Passou' : '? Falhou'}</td>
                <td>${test.duration}ms</td>
                <td>
                    <button class="details-btn">Detalhes</button>
                    <div class="details-content">${test.failureMessages || '-'}</div>
                </td>
            `;

            resultsBody.appendChild(row);

            if (test.status === 'passed') passed++;
            else failed++;

            // Adiciona evento para mostrar detalhes
            row.querySelector('.details-btn').addEventListener('click', (e) => {
                const details = e.target.nextElementSibling;
                details.style.display = details.style.display === 'block' ? 'none' : 'block';
            });
        });
    });

    // Atualiza estatísticas
    document.getElementById('passed').textContent = passed;
    document.getElementById('failed').textContent = failed;
    document.getElementById('total').textContent = passed + failed;

    // Atualiza barra de progresso
    const progress = (passed / (passed + failed)) * 100;
    document.querySelector('.progress').style.width = `${progress}%`;

    // Em dashboard/script.js
    fetch('../test-report-processor/reports/test-results.json')
      .then(response => response.json())
      .then(data => updateDashboard(data));
}