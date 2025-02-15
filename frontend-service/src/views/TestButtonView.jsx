// src/views/TestButtonView.jsx
import { ref } from 'vue'

export default function TestButtonView() {
  const response = ref('')
  const error = ref(null)
  const loading = ref(false)

  const handleClick = async () => {
    loading.value = true
    error.value = null

    try {
      console.log('Making API call...')
      const result = await fetch('http://localhost:8081/api/advertisements/test', {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      })
      console.log('Response status:', result.status)

      if (!result.ok) {
        throw new Error(`HTTP error! status: ${result.status}`)
      }

      const data = await result.json()
      response.value = JSON.stringify(data, null, 2)
      console.log('Response data:', data)
    } catch (err) {
      console.error('Error:', err)
      error.value = `Error: ${err.message}`
    } finally {
      loading.value = false
    }
  }

  return (
    <div style={{ padding: '20px' }}>
      <button
        onClick={handleClick}
        disabled={loading.value}
        style={{
          padding: '10px 20px',
          backgroundColor: '#4CAF50',
          color: 'white',
          border: 'none',
          borderRadius: '4px',
          cursor: loading.value ? 'default' : 'pointer'
        }}
      >
        {loading.value ? 'Loading...' : 'Test API Call'}
      </button>

      {error.value && (
        <div style={{
          marginTop: '20px',
          color: 'red',
          padding: '10px',
          border: '1px solid red',
          borderRadius: '4px'
        }}>
          {error.value}
        </div>
      )}

      {response.value && (
        <pre style={{ 
          marginTop: '20px',
          padding: '15px',
          backgroundColor: '#f5f5f5',
          borderRadius: '4px',
          overflow: 'auto'
        }}>
          {response.value}
        </pre>
      )}
    </div>
  )
}