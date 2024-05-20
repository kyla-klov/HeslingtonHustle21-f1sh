const fs = require('fs');

// Read the GitHub event payload to get the issue body
const githubEventPath = process.env.GITHUB_EVENT_PATH;
const githubEvent = JSON.parse(fs.readFileSync(githubEventPath, 'utf8'));
const issueBody = githubEvent.issue.body;

// Parse the new player data from the issue body
const newPlayerData = JSON.parse(issueBody);

// Read existing data from the file
const rawData = fs.readFileSync('ScoreboardData.json');
const jsonData = JSON.parse(rawData);

// Update the data with new player data
jsonData.players.push(newPlayerData);

// Write the updated data back to the file
fs.writeFileSync('ScoreboardData.json', JSON.stringify(jsonData, null, 2));