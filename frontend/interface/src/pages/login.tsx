'use client'
import { useState } from 'react'
import { useRouter } from 'next/navigation'
import api from '../api/api'
import Cookies from 'js-cookie'

export default function LoginPage() {
    const [email, setEmail] = useState('')
    const [senha, setSenha] = useState('')
    const [error, setError] = useState('')
    const router = useRouter()

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault()
        setError('')

        try {
            const response = await api.post('/auth/login', { email, senha })
            const token = response.headers['authorization']

            if (token) {
                Cookies.set('token', token.split(' ')[1], { expires: 1 })
                router.push('/profile') // Redireciona para a página de perfil
            } else {
                setError('Erro ao autenticar. Verifique suas credenciais.')
            }
        } catch (err) {
            setError('Usuário ou senha inválidos.')
        }
    }

    return (
        <div className="flex flex-col items-center justify-center min-h-screen">
            <h1 className="text-3xl font-bold mb-4">Login</h1>
            <form
                onSubmit={handleLogin}
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
                    Entrar
                </button>
            </form>
            <p className="mt-4">
                Ainda não tem uma conta?{' '}
                <a href="/register" className="text-blue-500 underline">
                    Cadastre-se
                </a>
            </p>
        </div>
    )
}
