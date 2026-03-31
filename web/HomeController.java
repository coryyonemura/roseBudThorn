package web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Rose Bud Thorn</title>
            <style>
            * { box-sizing: border-box; }
            body { margin: 0; font-family: system-ui, sans-serif; background: #fdfbf9; color: #1a1a1a; padding: 2rem; }
            .container { max-width: 720px; margin: 0 auto; background: #fff; padding: 1.5rem; border-radius: 12px; box-shadow: 0 2px 12px rgba(0,0,0,0.06); border: 1px solid #e5e0d8; }
            h1 { font-size: 1.75rem; margin: 0 0 0.25rem 0; }
            h2 { font-size: 1.25rem; margin: 0 0 0.5rem 0; }
            .tagline { margin: 0 0 1.25rem 0; font-size: 0.9rem; color: #5c5c5c; }
            label { display: block; font-weight: 600; font-size: 0.9rem; margin-bottom: 0.35rem; }
            .hint { font-weight: 400; font-size: 0.8rem; color: #5c5c5c; }
            textarea { font: inherit; padding: 0.5rem 0.75rem; border: 1px solid #e5e0d8; border-radius: 8px; background: #fdfbf9; width: 100%; min-height: 80px; resize: vertical; }
            .field { margin-bottom: 1.25rem; }
            .char-count { font-size: 0.8rem; color: #5c5c5c; text-align: right; margin-top: 0.25rem; }
            .char-count.at-limit { color: #b91c1c; font-weight: 600; }
            #status { margin: 1rem 0; padding: 0.75rem; border-radius: 8px; font-size: 0.9rem; display: none; }
            #status.success { background: #e8f0e6; color: #2d5a27; display: block; }
            #status.error { background: #fef2f2; color: #b91c1c; display: block; }
            button { font: inherit; font-weight: 600; padding: 0.75rem 1.25rem; background: #4a3728; color: #fff; border: none; border-radius: 8px; cursor: pointer; transition: opacity 0.15s; }
            button:hover { opacity: 0.9; }
            button:disabled { opacity: 0.6; cursor: not-allowed; }
            .btnRow { display: flex; gap: 0.75rem; flex-wrap: wrap; align-items: center; }
            .btnSecondary { background: #e5e0d8; color: #1a1a1a; }
            .todayRow { display: flex; align-items: baseline; justify-content: space-between; gap: 1rem; margin: 0 0 1.25rem 0; padding-bottom: 0.75rem; border-bottom: 1px solid #e5e0d8; }
            .todayRow .todayLabel { color: #5c5c5c; font-size: 0.9rem; }
            .todayRow .todayValue { font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace; font-weight: 700; }
            #submittedPane { display: none; margin-top: 1rem; }

            /* Calendar modal */
            .modalOverlay { position: fixed; inset: 0; background: rgba(0,0,0,0.35); display: none; align-items: center; justify-content: center; padding: 1rem; }
            .modalOverlay.open { display: flex; }
            .modal { width: 100%; max-width: 720px; background: #fff; border-radius: 12px; border: 1px solid #e5e0d8; box-shadow: 0 2px 24px rgba(0,0,0,0.15); overflow: hidden; }
            .modalHeader { display: flex; align-items: center; justify-content: space-between; gap: 0.75rem; padding: 1rem; border-bottom: 1px solid #e5e0d8; }
            .modalHeader .monthTitle { font-weight: 800; }
            .modalHeader .leftRight { display: flex; gap: 0.5rem; flex-wrap: wrap; }
            .modalBody { padding: 1rem; display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }
            .calendarGrid { display: grid; grid-template-columns: repeat(7, 1fr); gap: 0.4rem; }
            .weekday { font-size: 0.75rem; color: #5c5c5c; font-weight: 700; text-align: center; padding-bottom: 0.25rem; }
            .dayCell { height: 44px; border: 1px solid #e5e0d8; border-radius: 10px; display: flex; align-items: center; justify-content: center; cursor: pointer; user-select: none; background: #fff; position: relative; }
            .dayCell.empty { cursor: default; background: transparent; border-color: transparent; }
            .dayCell.hasEntry { border-color: #2d5a27; background: #dff2d8; border-width: 1px; }
            .dayCell.hasEntry::after { content: none; }
            .dayCell.selected { outline: 2px solid rgba(45,90,39,0.35); }
            .calendarSide { border: 1px solid #e5e0d8; border-radius: 12px; padding: 0.75rem; background: #fdfbf9; }
            .selectedDate { font-weight: 800; margin-bottom: 0.75rem; }
            .previewBlock { margin-bottom: 0.65rem; }
            .previewLabel { font-size: 0.85rem; font-weight: 800; margin-bottom: 0.15rem; }
            .previewText { white-space: pre-wrap; font-size: 0.9rem; color: #1a1a1a; }
            .noEntry { color: #5c5c5c; font-size: 0.95rem; }
            </style>
            </head>
            <body>
            <div class="container">
            <h1>Rose Bud Thorn</h1>
            <p class="tagline">Something thankful | something to grow | something difficult</p>

            <div class="todayRow">
              <div class="todayLabel">Today's date</div>
              <div class="todayValue" id="todayValue">----</div>
            </div>

            <div id="editPane">
              <form id="entryForm">
              <div class="field">
              <label for="rose">Rose <span class="hint">Something you're thankful for</span></label>
              <textarea id="rose" placeholder="Write your rose..." maxlength="280" rows="3"></textarea>
              <div class="char-count" id="roseCount">0 / 280</div>
              </div>
              <div class="field">
              <label for="bud">Bud <span class="hint">Something you want to improve on tomorrow</span></label>
              <textarea id="bud" placeholder="Write your bud..." maxlength="280" rows="3"></textarea>
              <div class="char-count" id="budCount">0 / 280</div>
              </div>
              <div class="field">
              <label for="thorn">Thorn <span class="hint">Something difficult today</span></label>
              <textarea id="thorn" placeholder="Write your thorn..." maxlength="280" rows="3"></textarea>
              <div class="char-count" id="thornCount">0 / 280</div>
              </div>
              <div id="status"></div>

              <div class="btnRow">
                <button type="submit" id="submitBtn">Save entry</button>
              </div>
              </form>
            </div>

            <div id="submittedPane">
              <h2>You've submitted for the day</h2>
              <p style="margin: 0 0 1rem 0; color: #5c5c5c;">You can edit your submission if you want.</p>
              <button type="button" id="editSubmissionBtn">Edit your submission</button>
            </div>

            <div class="btnRow" style="margin-top: 1rem;">
              <button type="button" id="calendarBtn" class="btnSecondary">Calendar</button>
            </div>
            </div>

            <div id="calendarOverlay" class="modalOverlay" aria-hidden="true">
              <div class="modal" role="dialog" aria-modal="true" aria-label="Calendar">
                <div class="modalHeader">
                  <div class="leftRight">
                    <button type="button" id="prevMonthBtn" class="btnSecondary">Prev</button>
                    <button type="button" id="todayBtn" class="btnSecondary">Today</button>
                    <button type="button" id="nextMonthBtn" class="btnSecondary">Next</button>
                  </div>
                  <div class="monthTitle" id="monthTitle">Month</div>
                  <button type="button" id="closeCalendarBtn" class="btnSecondary">Close</button>
                </div>
                <div class="modalBody">
                  <div class="calendarGrid" id="calendarGrid"></div>
                  <div class="calendarSide">
                    <div class="selectedDate" id="selectedDate">Select a day</div>
                    <div id="selectedPreview"></div>
                    <div id="selectedHint" class="noEntry" style="display:none;">No entry found for this day.</div>
                  </div>
                </div>
              </div>
            </div>

            <script>
            const MAX = 280;
            const userId = 1;
            const editPane = document.getElementById('editPane');
            const submittedPane = document.getElementById('submittedPane');
            const editSubmissionBtn = document.getElementById('editSubmissionBtn');

            const roseEl = document.getElementById('rose');
            const budEl = document.getElementById('bud');
            const thornEl = document.getElementById('thorn');
            const form = document.getElementById('entryForm');
            const statusEl = document.getElementById('status');
            const submitBtn = document.getElementById('submitBtn');

            const calendarOverlay = document.getElementById('calendarOverlay');
            const calendarBtn = document.getElementById('calendarBtn');
            const closeCalendarBtn = document.getElementById('closeCalendarBtn');
            const prevMonthBtn = document.getElementById('prevMonthBtn');
            const todayBtn = document.getElementById('todayBtn');
            const nextMonthBtn = document.getElementById('nextMonthBtn');
            const monthTitle = document.getElementById('monthTitle');
            const calendarGrid = document.getElementById('calendarGrid');
            const selectedDateEl = document.getElementById('selectedDate');
            const selectedPreviewEl = document.getElementById('selectedPreview');
            const selectedHintEl = document.getElementById('selectedHint');

            let currentYear = new Date().getFullYear();
            let currentMonthIndex = new Date().getMonth(); // 0-11
            let entryByDay = {};
            let selectedDayStr = null;
            let lastSaved = null;

            function today() {
              const d = new Date();
              return d.getFullYear() + '-' + String(d.getMonth() + 1).padStart(2, '0') + '-' + String(d.getDate()).padStart(2, '0');
            }
            function toYMD(date) {
              return date.getFullYear() + '-' + String(date.getMonth() + 1).padStart(2, '0') + '-' + String(date.getDate()).padStart(2, '0');
            }
            function extractYMD(value) {
              if (value === null || value === undefined) return null;
              if (typeof value === 'string') {
                // Expecting something like YYYY-MM-DD or YYYY-MM-DDTHH:mm:ss.SSSZ
                return value.slice(0, 10);
              }
              if (typeof value === 'number') {
                const d = new Date(value);
                if (Number.isNaN(d.getTime())) return null;
                return toYMD(d);
              }
              try {
                const d = new Date(value);
                if (Number.isNaN(d.getTime())) return null;
                return toYMD(d);
              } catch (e) {
                return null;
              }
            }
            function formatPretty(date) {
              return date.toLocaleDateString(undefined, { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' });
            }

            document.getElementById('todayValue').textContent = today();

            function updateCount(el, countEl) {
              countEl.textContent = el.value.length + ' / ' + MAX;
              countEl.classList.toggle('at-limit', el.value.length >= MAX);
            }
            roseEl.addEventListener('input', () => updateCount(roseEl, document.getElementById('roseCount')));
            budEl.addEventListener('input', () => updateCount(budEl, document.getElementById('budCount')));
            thornEl.addEventListener('input', () => updateCount(thornEl, document.getElementById('thornCount')));
            updateCount(roseEl, document.getElementById('roseCount'));
            updateCount(budEl, document.getElementById('budCount'));
            updateCount(thornEl, document.getElementById('thornCount'));

            editSubmissionBtn.addEventListener('click', () => {
              if (lastSaved) {
                roseEl.value = lastSaved.rose || '';
                budEl.value = lastSaved.bud || '';
                thornEl.value = lastSaved.thorn || '';
                updateCount(roseEl, document.getElementById('roseCount'));
                updateCount(budEl, document.getElementById('budCount'));
                updateCount(thornEl, document.getElementById('thornCount'));
              }
              submittedPane.style.display = 'none';
              editPane.style.display = 'block';
              statusEl.style.display = 'none';
            });

            form.addEventListener('submit', async (e) => {
              e.preventDefault();
              statusEl.className = '';
              statusEl.textContent = '';
              statusEl.style.display = 'none';
              submitBtn.disabled = true;
              submitBtn.textContent = 'Saving...';

              const params = new URLSearchParams({
                userId: String(userId),
                date: today(),
                rose: roseEl.value.trim(),
                bud: budEl.value.trim(),
                thorn: thornEl.value.trim()
              });

              try {
                const res = await fetch('/api/entries', {
                  method: 'POST',
                  headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                  body: params
                });
                if (res.ok) {
                  const saved = await res.json();
                  lastSaved = saved;
                  roseEl.value = saved.rose || '';
                  budEl.value = saved.bud || '';
                  thornEl.value = saved.thorn || '';
                  updateCount(roseEl, document.getElementById('roseCount'));
                  updateCount(budEl, document.getElementById('budCount'));
                  updateCount(thornEl, document.getElementById('thornCount'));
                  editPane.style.display = 'none';
                  submittedPane.style.display = 'block';
                } else {
                  const text = await res.text();
                  statusEl.className = 'error';
                  statusEl.textContent = text || 'Something went wrong.';
                  statusEl.style.display = 'block';
                }
              } catch (err) {
                statusEl.className = 'error';
                statusEl.textContent = err.message || 'Network error.';
                statusEl.style.display = 'block';
              }

              submitBtn.disabled = false;
              submitBtn.textContent = 'Save entry';
            });

            function openCalendar() {
              calendarOverlay.classList.add('open');
              calendarOverlay.setAttribute('aria-hidden', 'false');
              loadMonth(currentYear, currentMonthIndex);
            }

            function closeCalendar() {
              calendarOverlay.classList.remove('open');
              calendarOverlay.setAttribute('aria-hidden', 'true');
            }

            calendarBtn.addEventListener('click', openCalendar);
            closeCalendarBtn.addEventListener('click', closeCalendar);
            calendarOverlay.addEventListener('click', (e) => {
              if (e.target === calendarOverlay) closeCalendar();
            });

            prevMonthBtn.addEventListener('click', () => {
              currentMonthIndex -= 1;
              if (currentMonthIndex < 0) { currentMonthIndex = 11; currentYear -= 1; }
              loadMonth(currentYear, currentMonthIndex);
            });

            todayBtn.addEventListener('click', () => {
              const now = new Date();
              currentYear = now.getFullYear();
              currentMonthIndex = now.getMonth();
              loadMonth(currentYear, currentMonthIndex);
            });

            nextMonthBtn.addEventListener('click', () => {
              currentMonthIndex += 1;
              if (currentMonthIndex > 11) { currentMonthIndex = 0; currentYear += 1; }
              loadMonth(currentYear, currentMonthIndex);
            });

            async function loadMonth(year, monthIndex) {
              selectedDayStr = null;
              entryByDay = {};
              selectedDateEl.textContent = 'Loading...';
              selectedPreviewEl.innerHTML = '';
              selectedHintEl.style.display = 'none';

              const first = new Date(year, monthIndex, 1);
              const last = new Date(year, monthIndex + 1, 0);
              const fromStr = toYMD(first);
              const toStr = toYMD(last);

              const res = await fetch(`/api/entries/range?userId=${userId}&from=${fromStr}&to=${toStr}`);
              const entries = await res.json();

              for (const entry of entries) {
                if (!entry || !entry.date) continue;
                const dayStr = extractYMD(entry.date);
                if (!dayStr) continue;
                entryByDay[dayStr] = entry;
              }

              const prettyMonth = new Date(year, monthIndex, 1).toLocaleDateString(undefined, { month: 'long', year: 'numeric' });
              monthTitle.textContent = prettyMonth;

              renderCalendarGrid(year, monthIndex);

              const t = new Date();
              if (t.getFullYear() === year && t.getMonth() === monthIndex) {
                selectDay(toYMD(t));
              } else {
                selectedDateEl.textContent = 'Select a day';
              }
            }

            function renderCalendarGrid(year, monthIndex) {
              calendarGrid.innerHTML = '';

              const weekdays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
              for (const w of weekdays) {
                const el = document.createElement('div');
                el.className = 'weekday';
                el.textContent = w;
                calendarGrid.appendChild(el);
              }

              const firstDay = new Date(year, monthIndex, 1).getDay();
              const daysInMonth = new Date(year, monthIndex + 1, 0).getDate();

              // 42 cells (6 weeks)
              for (let cell = 0; cell < 42; cell++) {
                const dayNum = cell - firstDay + 1;
                const dayCell = document.createElement('div');
                dayCell.className = 'dayCell';

                if (dayNum < 1 || dayNum > daysInMonth) {
                  dayCell.classList.add('empty');
                  dayCell.textContent = '';
                  dayCell.style.pointerEvents = 'none';
                } else {
                  const dayDate = new Date(year, monthIndex, dayNum);
                  const dayStr = toYMD(dayDate);
                  dayCell.textContent = String(dayNum);
                  if (entryByDay[dayStr]) dayCell.classList.add('hasEntry');
                  if (selectedDayStr === dayStr) dayCell.classList.add('selected');
                  dayCell.addEventListener('click', () => selectDay(dayStr));
                }

                calendarGrid.appendChild(dayCell);
              }
            }

            function escapeHtml(s) {
              return String(s)
                .replaceAll('&', '&amp;')
                .replaceAll('<', '&lt;')
                .replaceAll('>', '&gt;')
                .replaceAll('"', '&quot;')
                .replaceAll("'", '&#039;');
            }

            function selectDay(dayStr) {
              selectedDayStr = dayStr;
              selectedDateEl.textContent = formatPretty(new Date(dayStr + 'T00:00:00'));

              // refresh highlight selection
              renderCalendarGrid(currentYear, currentMonthIndex);

              const entry = entryByDay[dayStr];
              if (!entry) {
                selectedPreviewEl.innerHTML = '';
                selectedHintEl.style.display = 'block';
                selectedHintEl.textContent = 'No entry found for this day.';
                return;
              }

              selectedHintEl.style.display = 'none';
              selectedPreviewEl.innerHTML = `
                <div class="previewBlock">
                  <div class="previewLabel">Rose</div>
                  <div class="previewText">${escapeHtml(entry.rose || '')}</div>
                </div>
                <div class="previewBlock">
                  <div class="previewLabel">Bud</div>
                  <div class="previewText">${escapeHtml(entry.bud || '')}</div>
                </div>
                <div class="previewBlock">
                  <div class="previewLabel">Thorn</div>
                  <div class="previewText">${escapeHtml(entry.thorn || '')}</div>
                </div>
              `;
            }
            </script>
            </body>
            </html>
            """;
    }
}
