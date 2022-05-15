//Flot Line Chart

const legend_chart_position = "right";
const duration_chart_options = {
    xaxes: [{
        mode: 'time',
        timeformat: '%h %p',
        mintickSize: [1, "hours"]
    }],
    yaxes: [{
        min: 0
    }, {
        // align if we are to the right
        alignTicksWithAxis: legend_chart_position === "right" ? 1 : null,
        position: legend_chart_position
    }],
    legend: {
        position: 'sw'
    },
    grid: {
        hoverable: true //IMPORTANT! this is needed for tooltip to work
    },
    tooltip: true,
    tooltipOpts: {
        content: "%s for %x was %y",
        xDateFormat: "%H:%M:%S",

        onHover: function (flotItem, $tooltipEl) {
            // console.log(flotItem, $tooltipEl);
        }
    }
};

$(document).ready(function () {
    $.plot($("#durationStandChart"), [{
        data: [],
        label: ''
    }], duration_chart_options);
});

function updateDurationStandChart(rowData) {
    let standId = rowData['standId'];
    let checkId =rowData['checkId'];
    let mediana = rowData['mediana'];

    $('#chartCaption').text(`Стенд ${rowData['standName']} работа проверки ${rowData['checkName']} за последние сутки`)

    let url = `/api/chart/durationsForStandAndCheck?standId=${standId}&checkId=${checkId}`;
    $.getJSON(url,
        function (data) {
           let currentOptions = duration_chart_options;
           currentOptions.yaxes[0].max = mediana * 20;

            let plot = $.plot($("#durationStandChart"), [{
                data: data['data'],
                label: data['label']
            }], currentOptions)
        });
}