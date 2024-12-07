/*=========================================================================================
    Tên File: bar.js
    Mô tả: Biểu đồ thanh bằng Chartjs cho dữ liệu bán hàng theo quý
==========================================================================================*/

// Biểu đồ thanh
// ------------------------------
$(window).on("load", function(){
    // Lấy ngữ cảnh của phần tử canvas biểu đồ
    var ctx = $("#bar-chart");

    // Tùy chọn biểu đồ
    var chartOptions = {
        elements: {
            rectangle: {
                borderWidth: 2,
                borderColor: 'rgb(0, 255, 0)',
                borderSkipped: 'left'
            }
        },
        responsive: true,
        maintainAspectRatio: false,
        responsiveAnimationDuration: 500,
        legend: {
            position: 'top',
        },
        scales: {
            xAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Số lượng bán'
                },
                gridLines: {
                    color: "#f3f3f3",
                    drawTicks: false,
                }
            }],
            yAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Quý'
                },
                gridLines: {
                    color: "#f3f3f3",
                    drawTicks: false,
                }
            }]
        },
        title: {
            display: true,
            text: 'Số lượng bán hàng theo quý'
        }
    };

    // Tìm nạp dữ liệu quý từ backend
    $.ajax({
        url: '/admin/bar/quarterData',
        method: 'GET',
        success: function(quarterData) {
            // Xử lý dữ liệu
            var labels = [];
            var quantities = [];

            quarterData.forEach(function(item) {
                // Giả định cấu trúc dữ liệu là [quý, số lượng, tổng, trung bình, min, max]
                labels.push('Quý ' + item[0]); // Chuyển số quý thành Quý 1, Quý 2, etc.
                quantities.push(item[1]);   // Số lượng ở chỉ mục 1
            });

            // Dữ liệu biểu đồ
            var chartData = {
                labels: labels,
                datasets: [{
                    label: "Số lượng bán",
                    data: quantities,
                    backgroundColor: "#28D094",
                    hoverBackgroundColor: "rgba(40,208,148,.9)",
                    borderColor: "transparent"
                }]
            };

            var config = {
                type: 'horizontalBar',
                options: chartOptions,
                data: chartData
            };

            // Tạo biểu đồ
            var quarterChart = new Chart(ctx, config);
        },
        error: function(xhr, status, error) {
            console.error("Lỗi khi tải dữ liệu quý:", error);
        }
    });
});