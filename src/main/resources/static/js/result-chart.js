// static/js/result-chart.js
window.addEventListener('DOMContentLoaded', () => {
  Chart.register(ChartDataLabels);

  const canvas     = document.getElementById('mbtiChart');
  const ctx        = canvas.getContext('2d');
  const labels     = mbtiLabels;   // ['E vs I','S vs N','T vs F','J vs P']
  const firstData  = mbtiFirst;    // [E%, S%, T%, J%]
  const secondData = mbtiSecond;   // [I%, N%, F%, P%]
  const grayColor  = 'rgba(200,200,200,0.5)';

  // ─── 色配列を「大きい方だけテーマカラー」に
  const bgColor1 = firstData.map((v, i) =>
    v >= secondData[i] ? themeColor : grayColor
  );
  const bgColor2 = secondData.map((v, i) =>
    v > firstData[i] ? themeColor : grayColor
  );

  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: labels,
      datasets: [
        { data: firstData,  backgroundColor: bgColor1, stack: 's1' },
        { data: secondData, backgroundColor: bgColor2, stack: 's1' }
      ]
    },
    options: {
      indexAxis: 'y',
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        x: { stacked: true, min: 0, max: 100, ticks: { callback: v => v + '%' } },
        y: { stacked: true, ticks: { display: false }, grid: { display: false } }
      },
      plugins: {
        legend:  { display: false },
        tooltip: { enabled: false },
        datalabels: {
          color: '#000',
          formatter: (value, ctx) => {
            const [a, b] = labels[ctx.dataIndex].split(' vs ');
            const isFirst = ctx.datasetIndex === 0;
            const percent = isFirst
              ? firstData[ctx.dataIndex]
              : secondData[ctx.dataIndex];
            const letter  = isFirst ? a : b;
            return `${letter} ${percent}%`;
          },
          anchor: ctx => ctx.datasetIndex === 0 ? 'start' : 'end',
          align:  ctx => ctx.datasetIndex === 0 ? 'end'   : 'start',
          padding: 4,
          clamp:   true,
          font: { size: 12, weight: 'bold' }
        }
      }
    }
  });
});