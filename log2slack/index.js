const { IncomingWebhook } = require("@slack/client");
const LOG_COLORS = {
    DEBUG: "#4175e1",
    INFO: "#76a9fa",
    WARNING: "warning",
    ERROR: "danger",
    CRITICAL: "#ff0000",
};
const SLACK_WEBHOOK_URL = "<実際のWebhook URLに置き換える>";

exports.log2slack = (event, callback) => {
    const data = JSON.parse(new Buffer(event.data.data, "base64").toString());

    const labels = data.resource.labels;
    const projectId = labels.project_id;
    console.log("labels:" + labels);

    const logUrl = `https://console.developers.google.com/logs?project=${projectId}`
        + `&expandAll=true&advancedFilter=resource.type%3D%22container%22%0A`
        + `insertId%3D%22${data.insertId}%22`;
    const body = {
        attachments: [{
            title: `<${logUrl}|Open Logging>`,
            text: "\`" + `${data.receiveTimestamp}` + "\` \`" + `${data.severity}` + "\` " + `${data.textPayload}` + "\n",
            color: LOG_COLORS[data.severity],
            fields: [
                { title: "Severity", value: data.severity, short: true },
                { title: "Timestamp", value: data.receiveTimestamp, short: true },
                { title: "ProjectId", value: projectId, short: true },
                { title: "ClusterName", value: labels.cluster_name, short: true },
                { title: "NamespaceId", value: labels.namespace_id, short: true },
                { title: "PodId", value: labels.pod_id, short: true },
            ]
        }]
    };

    const webhook = new IncomingWebhook(SLACK_WEBHOOK_URL);
    webhook.send(body, (err, res) => {
        if (err) { console.error(err); }
        if (res) { console.log(res); }
        callback();
    });
};
