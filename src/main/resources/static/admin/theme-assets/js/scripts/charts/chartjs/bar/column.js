$(window).on("load", function () {
    // Gọi API để lấy dữ liệu
    $.ajax({
        url: '/admin/bar/monthData', // URL API
        method: 'GET',
        success: function (response) {
            // Chuẩn bị dữ liệu cho biểu đồ
            let labels = [];
            let data = [];

            // Duyệt qua dữ liệu API để lấy thông tin
            response.forEach(item => {
                labels.push(`Tháng ${item[0]}`); // item[0]: Tên tháng
                data.push(item[1]); // item[1]: Số lượng
            });

            // Cấu hình biểu đồ
            var ctx = $("#column-chart");

            // Chart Options
            var chartOptions = {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    xAxes: [{
                        display: true,
                        gridLines: {
                            color: "#f3f3f3",
                            drawTicks: false,
                        },
                    }],
                    yAxes: [{
                        display: true,
                        gridLines: {
                            color: "#f3f3f3",
                            drawTicks: false,
                        },
                        ticks: {
                            beginAtZero: true // Bắt đầu từ 0
                        }
                    }]
                },
                title: {
                    display: true,
                    text: 'Số lượng bán hàng theo tháng'
                }
            };

            // Chart Data
            var chartData = {
                labels: labels,
                datasets: [{
                    label: "Số lượng bán",
                    data: data,
                    backgroundColor: "#28D094",
                    hoverBackgroundColor: "rgba(40,208,148,.9)",
                    borderColor: "transparent"
                }]
            };

            // Cấu hình và vẽ biểu đồ
            var config = {
                type: 'bar',
                options: chartOptions,
                data: chartData
            };

            var lineChart = new Chart(ctx, config);
        },
        error: function (error) {
            console.error('Lỗi khi lấy dữ liệu từ API:', error);
        }
    });
});