$(window).on("load", function(){
    // Gọi API để lấy dữ liệu
    $.ajax({
        url: '/admin/bar/quarterData', // URL API
        method: 'GET',
        success: function (response) {
            // Kiểm tra dữ liệu trả về
            if (!response || !Array.isArray(response) || response.length === 0) {
                console.error('Dữ liệu API không hợp lệ:', response);
                return;
            }

            // Chuẩn bị dữ liệu cho biểu đồ
            let labels = [];
            let datasets = [
                {
                    label: "Số lượng",
                    data: [],
                    backgroundColor: "#1E9FF2",
                    hoverBackgroundColor: "rgba(30,159,242,.9)",
                    borderColor: "transparent"
                },
                {
                    label: "Tổng tiền",
                    data: [],
                    backgroundColor: "#FF4961",
                    hoverBackgroundColor: "rgba(255,73,97,.9)",
                    borderColor: "transparent"
                }
            ];

            // Duyệt qua dữ liệu API để lấy thông tin
            response.forEach(item => {
                // Kiểm tra tính hợp lệ của từng mục
                if (Array.isArray(item) && item.length >= 3) {
                    labels.push(item[0]); // Tên danh mục
                    datasets[0].data.push(item[1]); // Số lượng
                    datasets[1].data.push(item[2]); // Tổng tiền
                } else {
                    console.warn('Mục dữ liệu không hợp lệ:', item);
                }
            });

            // Kiểm tra xem có dữ liệu để vẽ không
            if (labels.length === 0) {
                console.error('Không có dữ liệu để vẽ biểu đồ');
                return;
            }

            // Chart Options
            var chartOptions = {
                title:{
                    display: true,
                    text: "Thống Kê Bán Hàng Theo Qúy"
                },
                tooltips: {
                    mode: 'label',
                    callbacks: {
                        label: function(tooltipItem, data) {
                            var datasetLabel = data.datasets[tooltipItem.datasetIndex].label || '';
                            var value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                            return `${datasetLabel}: ${value.toLocaleString()}`;
                        }
                    }
                },
                responsive: true,
                maintainAspectRatio: false,
                responsiveAnimationDuration: 500,
                scales: {
                    xAxes: [{
                        stacked: true,
                        display: true,
                        gridLines: {
                            color: "#f3f3f3",
                            drawTicks: false,
                        },
                        scaleLabel: {
                            display: true,
                            labelString: 'Qúy'
                        }
                    }],
                    yAxes: [{
                        stacked: true,
                        display: true,
                        gridLines: {
                            color: "#f3f3f3",
                            drawTicks: false,
                        },
                        scaleLabel: {
                            display: true,
                            labelString: 'Giá Trị'
                        },
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            };

            // Chart Data
            var chartData = {
                labels: labels,
                datasets: datasets
            };

            var config = {
                type: 'bar',
                options: chartOptions,
                data: chartData
            };

            // Get the context of the Chart canvas element we want to select
            var ctx = $("#column-stacked");

            // Create the chart
            var stackedChart = new Chart(ctx, config);
        },
        error: function (xhr, status, error) {
            console.error('Chi tiết lỗi API:', {
                status: xhr.status,
                responseText: xhr.responseText,
                error: error
            });
        }
    });
});