'use client'
import { useEffect, useState } from 'react'
import api from '../api/api'
import Cookies from 'js-cookie'
import { useRouter } from 'next/navigation'

export default function ProfilePage() {
    const [user, setUser] = useState<any>(null)
    const [error, setError] = useState('')
    const router = useRouter()

    useEffect(() => {
        const token = Cookies.get('token')
        if (!token) {
            router.push('/login') // Redireciona para o login se não houver token
            return
        }

        const fetchUserData = async () => {
            try {
                const response = await api.get('/users/me', {
                    headers: { Authorization: `Bearer ${token}` },
                })
                setUser(response.data)
            } catch (err) {
                setError('Erro ao carregar dados do usuário.')
            }
        }

        fetchUserData()
    }, [router])

    const handleLogout = () => {
        Cookies.remove('token')
        Cookies.remove('user')
        router.push('/login') // Redireciona para a página de login
    }

    if (error) return <p>{error}</p>

    return (
        <div className="flex flex-col items-center justify-center min-h-screen">
            <h1 className="text-3xl font-bold mb-4">Perfil do Usuário</h1>
            {user ? (
                <div className="space-y-3">
                    {/* Removido a exibição do email */}
                    <p>
                        <strong>Nome:</strong> {user.name || 'Não disponível'}
                    </p>
                    <p>
                        <strong>Telefone:</strong>{' '}
                        {user.phone || 'Não disponível'}
                    </p>
                    <p>
                        <strong>Website:</strong>{' '}
                        {user.website || 'Não disponível'}
                    </p>
                    {/* Exibindo o endereço corretamente, caso exista */}
                    <p>
                        <strong>Endereço:</strong>
                        {user.address
                            ? `${user.address.street || 'Rua desconhecida'}, ${
                                  user.address.city || 'Cidade desconhecida'
                              }`
                            : 'Não disponível'}
                    </p>
                    {/* Exibindo a empresa corretamente, caso exista */}
                    <p>
                        <strong>Empresa:</strong>
                        {user.company ? user.company.name : 'Não disponível'}
                    </p>
                    <button
                        onClick={() => router.push('/users')}
                        className="p-2 bg-blue-500 text-white rounded"
                    >
                        Voltar para a lista de usuários
                    </button>
                </div>
            ) : (
                <p>Carregando...</p>
            )}
            <button
                onClick={handleLogout}
                className="p-2 bg-red-500 text-white rounded mt-4"
            >
                Sair
            </button>
        </div>
    )
}
