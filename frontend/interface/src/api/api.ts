import axios from 'axios'

const api = axios.create({
    baseURL: 'http://localhost:8080', // Certifique-se que a URL está correta
    headers: {
        'Content-Type': 'application/json',
    },
})

export default api
