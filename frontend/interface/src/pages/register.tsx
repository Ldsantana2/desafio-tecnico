'use client'
import { useState } from 'react'
import { useRouter } from 'next/navigation'
import api from '../api/api'

export default function RegisterPage() {
    const [email, setEmail] = useState('')
    const [senha, setSenha] = useState('')
    const [error, setError] = useState('')
    const router = useRouter()

    const handleRegister = async (e: React.FormEvent) => {
        e.preventDefault()
        setError('')

        try {
            const response = await api.post('/users', { email, senha })
            if (response.status === 200) {
                router.push('/login')
            }
        } catch (err) {
            setError('Erro ao cadastrar usuário. Tente novamente.')
        }
    }

    return (
        <div className="flex flex-col items-center justify-center min-h-screen">
            <h1 className="text-3xl font-bold mb-4">Cadastro</h1>
            <form
                onSubmit={handleRegister}
                className="flex flex-col space-y-3 w-72"
            >
                <input
                    type="email"
                    placeholder="E-mail"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="p-2 border rounded"
                    required
                />
                <input
                    type="password"
                    placeholder="Senha"
                    value={senha}
                    onChange={(e) => setSenha(e.target.value)}
                    className="p-2 border rounded"
                    required
                />
                {error && <p className="text-red-500">{error}</p>}
                <button
                    type="submit"
                    className="p-2 bg-blue-500 text-white rounded"
                >
                    Cadastrar
                </button>
            </form>
        </div>
    )
}
