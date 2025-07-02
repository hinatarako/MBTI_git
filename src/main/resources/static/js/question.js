window.addEventListener('DOMContentLoaded', () => {
  Chart.register(ChartDataLabels);

  // グローバル変数（既存のまま）
  const labels  = mbtiLabels;  // 例: ['E vs I', 'S vs N', 'T vs F', 'J vs P']
  const dataMax = mbtiMax;
  const dataMin = mbtiMin;

  // 対立軸ごとの色マップ
  const axisColor = {
    E: '#c6a5e0',  // INTJ/INTP/ENTJ/ENTP 系 → 淡い紫
    I: '#c6a5e0',
    S: '#a3d5f7',  // ISTJ/ISFJ/ESTJ/ESFJ 系 → 薄い水色
    N: '#a3d5f7',
    T: '#c2e7c3',  // INFJ/INFP/ENFJ/ENFP 系 → 薄い黄緑
    F: '#c2e7c3',
    J: '#f9e7ab',  // ISTP/ISFP/ESTP/ESFP 系 → 薄い黄色
    P: '#f9e7ab'
  };

  // ラベルから対立軸の先頭文字・末尾文字を切り出し、色を取得
  const maxColors = labels.map(label => {
    const first = label.split(' vs ')[0];  // 'E' など
    return axisColor[first] || '#cccccc';
  });
  const minColors = labels.map(label => {
    const second = label.split(' vs ')[1]; // 'I' など
    return axisColor[second] || 'rgba(200,200,200,0.5)';
  });

  // チャート描画
  const ctx = document.getElementById('mbtiChart').getContext('2d');
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
        x: { stacked: true, min: 0, max: 100, ticks: { callback: v => v + '%' } },
        y: { stacked: true, ticks: { display: false }, grid: { display: false } }
      },
      plugins: {
        legend: { display: false },
        tooltip: {
          callbacks: {
            label: ctx => `${labels[ctx.dataIndex]}: ${ctx.parsed.x}%`
          }
        },
        datalabels: {
          color: '#000',
          formatter: (value, ctx) => labels[ctx.dataIndex],
          anchor: ctx => ctx.datasetIndex === 0 ? 'start' : 'end',
          align:  ctx => ctx.datasetIndex === 0 ? 'end' : 'start',
          padding: 4, clamp: true,
          font: { size: 12, weight: 'bold' }
        }
      }
    }
  });
});