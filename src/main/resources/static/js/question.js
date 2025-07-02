// static/js/question.js

document.addEventListener('DOMContentLoaded', () => {
  const pages = document.querySelectorAll('.question-page');
  pages.forEach(initPage);
});

function initPage(page) {
  const buttons = page.querySelectorAll('.circular-button');
  buttons.forEach(btn => {
    btn.addEventListener('click', () => {
      selectAnswer(btn);
      updateNavButtons(page);
    });
  });
}

function selectAnswer(btn) {
  const container = btn.parentElement;
  // 同じ質問の他ボタンは解除
  container.querySelectorAll('.circular-button')
           .forEach(b => b.classList.remove('selected'));
  // 選択ボタンにマーク
  btn.classList.add('selected');

  // hidden input を生成・更新
  const form  = document.querySelector('form');
  const name  = btn.getAttribute('data-name');
  const value = btn.getAttribute('data-value');
  let input = form.querySelector(`input[name="${name}"]`);
  if (!input) {
    input = document.createElement('input');
    input.type  = 'hidden';
    input.name  = name;
    form.appendChild(input);
  }
  input.value = value;
}

function updateNavButtons(page) {
  const questions = page.querySelectorAll('.question');
  const answered  = Array.from(questions)
                          .filter(q => q.querySelector('.circular-button.selected'))
                          .length;
  const all       = questions.length;

  // Next ボタン
  const next = page.querySelector('.nav-button[data-next]');
  if (next) next.disabled = (answered < all);

  // Submit ボタン（最終ページ）
  const submit = page.querySelector('.submit-button');
  if (submit) submit.disabled = (answered < all);
}

// ページ切替（Next / Back）
document.addEventListener('click', e => {
  if (!e.target.matches('.nav-button')) return;

  // 移動先インデックス取得
  const idx = parseInt(
    e.target.getAttribute('data-next') ?? 
    e.target.getAttribute('data-prev')
  );

  // 現在ページ非表示
  const current = document.querySelector('.question-page:not(.hidden)');
  current.classList.add('hidden');

  // 次ページ表示
  const target = document.getElementById('page' + idx);
  target.classList.remove('hidden');

  // ボタン状態を更新
  updateNavButtons(target);

  // ページ先頭までスクロール
  target.scrollIntoView({
    behavior: 'smooth',
    block:    'start'
  });
});