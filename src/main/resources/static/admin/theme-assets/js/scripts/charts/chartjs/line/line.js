$(window).on("load", function(){
    var ctx = $("#line-chart");

    // Fetch data from backend
    $.ajax({
        url: '/admin/bar/categoryConsumption',
        method: 'GET',
        success: function(data) {
            // Process data
            let categoryData = {};
            let months = new Set();

            // Organize data by category, filtering out negative values
            data.forEach(item => {
                let [categoryName, month, quantity] = item;

                // Skip negative quantities
                if (quantity > 0) {
                    months.add(month);

                    if (!categoryData[categoryName]) {
                        categoryData[categoryName] = {};
                    }
                    categoryData[categoryName][month] = quantity;
                }
            });

            // Prepare chart data
            let labels = Array.from(months).sort((a, b) => a - b)
                .map(month => ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][month - 1]);

            let datasets = Object.keys(categoryData).map((category, index) => ({
                label: category,
                data: labels.map((_, monthIndex) =>
                    categoryData[category][monthIndex + 1] || 0
                ),
                fill: false,
                borderColor: getUniqueColor(index),
                pointBorderColor: getUniqueColor(index),
                pointBackgroundColor: "#FFF",
                pointBorderWidth: 2,
                pointHoverBorderWidth: 2,
                pointRadius: 4,
            }));

            // Chart Options
            var chartOptions = {
                responsive: true,
                maintainAspectRatio: false,
                legend: {
                    position: 'bottom',
                },
                scales: {
                    xAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'Month'
                        }
                    }],
                    yAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'Quantity'
                        }
                    }]
                },
                title: {
                    display: true,
                    text: 'Category Consumption by Month'
                }
            };

            // Create chart configuration
            var config = {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: datasets
                },
                options: chartOptions
            };

            // Create the chart
            var lineChart = new Chart(ctx, config);
        }
    });

    // Function to generate unique colors
    function getUniqueColor(index) {
        const colors = [
            '#9C27B0', '#00A5A8', '#FF7D4D',
            '#4CAF50', '#2196F3', '#FF5722',
            '#673AB7', '#009688', '#FFC107'
        ];
        return colors[index % colors.length];
    }
});