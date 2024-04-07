<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<style>
body {
    font-family: Roboto, sans-serif;
}

#pieChart {
    max-width: 400px;
    margin: 35px auto;
}
#barChart {
    max-width: 400px;
    margin: 35px auto;
}
</style>

<div id="pieChart">
</div>

<div id="barChart">
</div>

<script>
    var chartYear = ${list1};
    var chartValue = ${list2};

    var pieOptions = {
        chart: {
            type: 'pie'
        },
        series: chartValue,
        labels: chartYear
    }
    var pieChart = new ApexCharts(document.querySelector("#pieChart"), pieOptions);
    pieChart.render();

    var barOptions = {
        chart: {
            type: 'bar'
        },
        series: [{
            //name: 'sales',
            data: chartValue
        }],
        xaxis: {
            categories: chartYear
        },
        /*states: {
            normal: {
                filter: {
                    type: 'none',
                    value: 0,
                }
            },
            hover: {
                filter: {
                    type: 'lighten',
                    value: 0.15,
                }
            },
            active: {
                allowMultipleDataPointsSelection: false,
                filter: {
                    type: 'darken',
                    value: 0.35,
                }
            },
        },*/
        legend: {
            show: true,
            showForSingleSeries: false,
            showForNullSeries: true,
            showForZeroSeries: true,
            position: 'bottom',
            horizontalAlign: 'center',
            floating: false,
            fontSize: '14px',
            fontFamily: 'Helvetica, Arial',
            fontWeight: 400,
            formatter: undefined,
            inverseOrder: false,
            width: undefined,
            height: undefined,
            tooltipHoverFormatter: undefined,
            customLegendItems: [],
            offsetX: 0,
            offsetY: 0,
            labels: {
                colors: undefined,
                useSeriesColors: false
            },
            markers: {
                width: 12,
                height: 12,
                strokeWidth: 0,
                strokeColor: '#fff',
                fillColors: undefined,
                radius: 12,
                customHTML: undefined,
                onClick: undefined,
                offsetX: 0,
                offsetY: 0
            },
            itemMargin: {
                horizontal: 5,
                vertical: 0
            },
            onItemClick: {
                toggleDataSeries: true
            },
            onItemHover: {
                highlightDataSeries: true
            },
        },
    }
    var barChart = new ApexCharts(document.querySelector("#barChart"), barOptions);
    barChart.render();

</script>

<script src="/js/chart.js"></script>

<%@ include file="../layout/footer.jsp"%>