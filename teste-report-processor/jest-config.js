module.exports = {
  reporters: [
    'default',
    ['jest-junit', {
      outputDirectory: './reports',
      outputName: 'junit-results.xml'
    }]
  ],
  testEnvironment: 'node',
  testMatch: ['**/*.test.js'],
  // Configura��es adicionais se necess�rio
};