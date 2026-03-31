import { useState } from 'react'
import './App.css'

const MAX_CHARS = 280
const USER_ID = 1

function todayStr() {
  const d = new Date()
  return d.getFullYear() + '-' + String(d.getMonth() + 1).padStart(2, '0') + '-' + String(d.getDate()).padStart(2, '0')
}

export default function App() {
  const [date, setDate] = useState(todayStr())
  const [rose, setRose] = useState('')
  const [bud, setBud] = useState('')
  const [thorn, setThorn] = useState('')
  const [status, setStatus] = useState(null)
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e) => {
    e.preventDefault()
    setStatus(null)
    setLoading(true)
    try {
      const params = new URLSearchParams({
        userId: String(USER_ID),
        date,
        rose: rose.trim(),
        bud: bud.trim(),
        thorn: thorn.trim(),
      })
      const res = await fetch('/api/entries', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params,
      })
      const data = await res.text()
      if (res.ok) {
        setStatus({ type: 'success', message: 'Entry saved.' })
        setRose('')
        setBud('')
        setThorn('')
      } else {
        setStatus({ type: 'error', message: data || 'Something went wrong.' })
      }
    } catch (err) {
      setStatus({ type: 'error', message: err.message || 'Network error.' })
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="app">
      <header className="header">
        <h1>Rose Bud Thorn</h1>
        <p className="tagline">Something thankful · something to grow · something difficult</p>
      </header>

      <main className="main">
        <form onSubmit={handleSubmit} className="entry-form">
          <div className="form-row">
            <label htmlFor="date">Date</label>
            <input
              id="date"
              type="date"
              value={date}
              onChange={(e) => setDate(e.target.value)}
              required
            />
          </div>

          <Field
            id="rose"
            label="Rose"
            hint="Something you're thankful for"
            value={rose}
            onChange={setRose}
            maxChars={MAX_CHARS}
            className="field-rose"
          />
          <Field
            id="bud"
            label="Bud"
            hint="Something you want to improve on tomorrow"
            value={bud}
            onChange={setBud}
            maxChars={MAX_CHARS}
            className="field-bud"
          />
          <Field
            id="thorn"
            label="Thorn"
            hint="Something difficult today"
            value={thorn}
            onChange={setThorn}
            maxChars={MAX_CHARS}
            className="field-thorn"
          />

          {status && (
            <div className={`status status-${status.type}`} role="alert">
              {status.message}
            </div>
          )}

          <button type="submit" className="submit-btn" disabled={loading}>
            {loading ? 'Saving…' : 'Save entry'}
          </button>
        </form>
      </main>
    </div>
  )
}

function Field({ id, label, hint, value, onChange, maxChars, className }) {
  const len = value.length
  const atLimit = len >= maxChars
  return (
    <div className={`field ${className}`}>
      <label htmlFor={id}>
        {label}
        <span className="hint">{hint}</span>
      </label>
      <textarea
        id={id}
        value={value}
        onChange={(e) => onChange(e.target.value.slice(0, maxChars))}
        placeholder={`Write your ${label.toLowerCase()}…`}
        rows={3}
        maxLength={maxChars}
      />
      <span className={`char-count ${atLimit ? 'at-limit' : ''}`}>
        {len} / {maxChars}
      </span>
    </div>
  )
}
