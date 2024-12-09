/*=========================================================================================
    Tên File: pie.js
    Mô tả: Biểu đồ tròn Chartjs hiển thị số lượng theo danh mục
==========================================================================================*/

// Biểu đồ tròn
// ------------------------------
$(window).on("load", function(){
    // Lấy ngữ cảnh của phần tử canvas biểu đồ
    var ctx = $("#simple-pie-chart");

    // Tùy chọn biểu đồ
    var chartOptions = {
        responsive: true,
        maintainAspectRatio: false,
        responsiveAnimationDuration: 500,
        title: {
            display: true,
            text: 'Số Lượng Sản Phẩm Theo Danh Mục'
        },
        tooltips: {
            callbacks: {
                label: function(tooltipItem, data) {
                    var dataset = data.datasets[tooltipItem.datasetIndex];
                    var total = dataset.data.reduce((acc, curr) => acc + curr, 0);
                    var currentValue = dataset.data[tooltipItem.index];
                    var percentage = Math.floor(((currentValue/total) * 100)+0.5);
                    return `${data.labels[tooltipItem.index]}: ${currentValue} (${percentage}%)`;
                }
            }
        }
    };

    // Tìm nạp dữ liệu danh mục từ backend
    $.ajax({
        url: '/admin/bar/catergoriesData',
        method: 'GET',
        success: function(categoryData) {
            // Xử lý dữ liệu
            var labels = [];
            var quantities = [];
            var colors = [
                '#666EE8', '#28D094', '#FF4961',
                '#1E9FF2', '#FF9149', '#7E57C2',
                '#4CAF50', '#FFC107', '#009688'
            ];

            categoryData.forEach(function(item) {
                // Giả định cấu trúc dữ liệu là [tên danh mục, số lượng, tổng, trung bình, min, max]
                labels.push(item[0]); // Tên danh mục ở chỉ mục 0
                quantities.push(item[1]); // Số lượng ở chỉ mục 1
            });

            // Dữ liệu biểu đồ
            var chartData = {
                labels: labels,
                datasets: [{
                    label: "Số Lượng Theo Danh Mục",
                    data: quantities,
                    backgroundColor: colors.slice(0, labels.length)
                }]
            };

            var config = {
                type: 'pie',
                options: chartOptions,
                data: chartData
            };

            // Tạo biểu đồ
            var categoryPieChart = new Chart(ctx, config);
        },
        error: function(xhr, status, error) {
            console.error("Lỗi khi tải dữ liệu danh mục:", error);
        }
    });
});