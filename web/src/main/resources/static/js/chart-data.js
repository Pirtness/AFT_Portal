$(document).ready(function () {

    window.chartColors = {
        red: 'rgb(255, 99, 132)',
        orange: 'rgb(255, 159, 64)',
        yellow: 'rgb(255, 205, 86)',
        green: 'rgb(75, 192, 192)',
        blue: 'rgb(54, 162, 235)',
        purple: 'rgb(153, 102, 255)',
        grey: 'rgb(201, 203, 207)'
    };

    let standId = 325;
    let checkId = 5;
    let dataJson = getJsonData(`/api/chart/durationsForStandAndCheck?standId=${standId}&checkId=${checkId}`)

    let timeFormat = 'MM/DD/YYYY H:mm';

    let color = Chart.helpers.color;

    let dataPointList = dataJson['data'].map(function (t) {
         return {x: t[0], y: t[1]}
    });

    let dataList = dataJson['data'].map(function (t) {
        return t[0]
    });
    let valueList = dataJson['data'].map(function (t) {
        return t[1]
    });

    let endDate = new Date(dataList[dataList.length - 1]);
    //let startDate = new Date(dataList[0] + -2*1000*60*60*24);
    let startDate = endDate;
    startDate.setHours(endDate.getHours()-2);

    let rangeMin = new Date(dataList[0]);  //start date
    rangeMin.setDate(rangeMin.getDate() - 10);

    let rangeMax = new Date(dataList[dataList.length - 1]);  //end date
    rangeMax.setDate(rangeMax.getDate() + 10);

    let ctx = document.getElementById('durationStandChart2').getContext('2d');
    let durationChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: dataList,
            datasets: [{
                label: 'duration',
                backgroundColor: color(window.chartColors.blue).alpha(0.8).rgbString(),
                borderColor: window.chartColors.red,
                fill: false,
                data: valueList,
            }]
        },
        options: {
            title: {
                text: 'duration for check'
            },
            scales: {
                xAxes: [{
                    type: 'time',
                    time: {
                        parser: timeFormat,
                        toolTipFormat: 'll HH:mm',
                        unit: 'hour'
                    },
                    distribution: 'linear',
                    scaleLabel: {
                        display: true,
                        labelString: 'Date'
                    },
                    ticks: {
                        max: startDate,
                        min: endDate
                    }
                }],
                yAxes: [{
                    ticks: {
                        max: 100,
                        min: 0
                    }
                }]
            },
            pan: {
                enabled: true,
                mode: 'xy',
                rangeMin: {
                    x: rangeMin,
                },
                rangeMax: {
                    x: rangeMax,
                },
            },
            zoom: {
                enabled: true,
                mode: 'x',
                threshold: 10,
                rangeMin: {
                    x: rangeMin,
                },
                rangeMax: {
                    x: rangeMax,
                },
            }
        }
    })
});