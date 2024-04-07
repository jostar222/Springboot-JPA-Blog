let index = {
    init:function () {
        $("#btn-export").on("click", ()=>{ //function(){}, ()=>{} this를 바인딩하기 위해서 화살표함수를 사용!!
            this.excelDown("excelTable", "ExcelData");
        });

        this.chart_render();
    },

    excelDown:function (id, title) {
        let html = "";

        html += "<html xmlns:x='urn:schemas-microsoft-com:office:excel' >";
        html += "   <haed>";
        html += "       <meta http-equiv='content-type' content='application/vnd.ms-excel; charset=UTF-8'>";
        html += "       <xml>";
        html += "           <x:ExcelWorkbook>";
        html += "               <x:ExcelWorksheets>";
        html += "                   <x:ExcelWorksheets>";
        html += "                       <x:name>Sheet</x:name>";
        html += "                       <x:WorksheetOptions><x:Panes></x:Panes></x:WorksheetOptions>";
        html += "                   </x:ExcelWorksheets>";
        html += "               </x:ExcelWorksheets>";
        html += "           </x:ExcelWorkbook>";
        html += "       </xml>";
        html += "   </haed>";

        html += "   <body>";
        html += "       <style>";
        html += "           .table th {background-color: cadetblue;}";
        html += "           .table th, td {border: thin solid gray;}";
        html += "       </style>";
        html += "           <table>";

        let exportTable = $('#' + id).clone();
        exportTable.find('input').each(function (index, elem) {
            $(elem).remove();
        });

        exportTable.find('#pieChart').each(function (index, elem) {
            $(elem).remove();
        });

        html +=             exportTable.html();
        html += "           </table>";
        html += "   </body>";
        html += "</html>";

        let data_type = "data:application/vnd.ms-excel";
        let ua = window.navigator.userAgent;
        let blob = new Blob([html], {type: "application/csv; charset=utf-8"});
        let fileName = title + '.xls';
        let anchor = window.document.createElement('a');

        anchor.href = window.URL.createObjectURL(blob);
        anchor.download = fileName;
        document.body.appendChild(anchor);
        anchor.click();

        //다운로드 후 제거
        document.body.removeChild(anchor);
    },
    chart_render:function() {
        let chartYear = [2000, 2005, 2010, 2015, 2020];
        let chartValue = [10000, 20000, 30000, 40000, 50000];

        let pieOptions = {
            chart: {
                type: 'pie',
                height: 400
            },
            series: chartValue,
            labels: chartYear
        }
        let pieChart = new ApexCharts(document.querySelector("#pieChart"), pieOptions);
        pieChart.render();
    },
}

index.init();