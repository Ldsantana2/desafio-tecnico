'use client'
import { useState, useEffect } from 'react'
import api from '../api/api'
import Cookies from 'js-cookie'
import { useRouter } from 'next/navigation'

export default function UsersPage() {
    const [users, setUsers] = useState<any[]>([])
    const router = useRouter()

    useEffect(() => {
        const token = Cookies.get('token')
        if (!token) {
            router.push('/login')
            return
        }

        const fetchUsers = async () => {
            try {
                const response = await api.get('/users', {
                    headers: { Authorization: `Bearer ${token}` },
                })
                setUsers(response.data)
            } catch (err) {
                console.error('Erro ao carregar os usuários.')
            }
        }

        fetchUsers()
    }, [router])

    const handleGoToProfile = () => {
        router.push('/profile')
    }

    const handleLogout = () => {
        Cookies.remove('token')
        Cookies.remove('user')
        router.push('/login')
    }

    return (
        <div className="flex flex-col items-center justify-center min-h-screen">
            <h1 className="text-3xl font-bold mb-4">Lista de Usuários</h1>
            <div className="space-y-3 mb-4">
                {users.map((user) => (
                    <div key={user.id} className="border p-4 rounded-lg">
                        <p>
                            <strong>Email:</strong> {user.email}
                        </p>
                    </div>
                ))}
            </div>
            <button
                onClick={handleGoToProfile}
                className="p-2 bg-blue-500 text-white rounded mb-4"
            >
                Ver Perfil do Usuário Logado
            </button>
            <button
                onClick={handleLogout}
                className="p-2 bg-red-500 text-white rounded mt-4"
            >
                Sair
            </button>
        </div>
    )
}
