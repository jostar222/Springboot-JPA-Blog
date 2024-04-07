<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!-- Trigger the modal with a button -->
<button type="button" class="btn btn-info btn-lg" id="btn-export">엑셀다운로드</button>

<div id="excelTable">
    <h1>엑셀다운로드 테스트</h1>
    <table class="table">
        <input type="text" value="Input tag 테스트용">
        <thead>
            <tr>
                <th>Column 1</th>
                <th>Column 2</th>
                <th>Column 3</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>데이터1_1</td>
                <td>데이터1_2</td>
                <td>데이터1_3</td>
            </tr>
            <tr>
                <td>데이터2_1</td>
                <td>데이터2_2</td>
                <td>데이터2_3</td>
            </tr>
            <tr>
                <td>데이터3_1</td>
                <td>데이터3_2</td>
                <td>데이터3_3</td>
            </tr>
        </tbody>
    </table>

    <%-- 차트 --%>
    <div id="pieChart"></div>

    <script>

    </script>
</div>

<script src="/js/excel.js"></script>

<%@ include file="../layout/footer.jsp"%>