window.addEventListener('DOMContentLoaded', () => {
  // プラグイン登録
  Chart.register(ChartDataLabels);

  // グローバル変数
  var labels     = mbtiLabels;
  var dataMax    = mbtiMax;
  var dataMin    = mbtiMin;
  var themeColor = barColor;

  // カラー配列
  var maxColors = dataMax.map(() => themeColor);
  var minColors = dataMin.map(() => 'rgba(200,200,200,0.6)');

  // チャート生成
  var ctx = document.getElementById('mbtiChart').getContext('2d');
  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: labels,
      datasets: [
        { data: dataMax, backgroundColor: maxColors, stack: 's1' },
        { data: dataMin, backgroundColor: minColors, stack: 's1' }
      ]
    },
    options: {
      indexAxis: 'y',
      responsive: true,
      maintainAspectRatio: true,
      scales: {
        x: {
          stacked: true,
          min: 0,
          max: 100,
          ticks: { callback: v => v + '%' }
        },
        y: {
          stacked: true,
          ticks: { display: false },
          grid: { display: false }
        }
      },
      plugins: {
        legend: { display: false },
        // ツールチップは「E60%」「I40%」
        tooltip: {
          callbacks: {
            label: ctx => {
              var parts = labels[ctx.dataIndex].split(' vs ');
              var letter = ctx.datasetIndex === 0 ? parts[0] : parts[1];
              return letter + ctx.parsed.x + '%';
            }
          }
        },
        // バー内ラベルは「E」「I」（％なし）、内側に寄せて切れないよう padding＋clamp
        datalabels: {
          color: '#000',
          formatter: (value, ctx) => {
            var parts = labels[ctx.dataIndex].split(' vs ');
            return ctx.datasetIndex === 0 ? parts[0] : parts[1];
          },
          anchor: ctx => ctx.datasetIndex === 0 ? 'start' : 'end',
          align:  ctx => ctx.datasetIndex === 0 ? 'end' : 'start',
          padding: 4,
          clamp: true,
          font: { size: 12, weight: 'bold' }
        }
      }
    }
  });
});