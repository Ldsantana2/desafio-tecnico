'use client'
import { useEffect, useState } from 'react'
import ProtectedRoute from '../components/ProtectedRoute'
import api from '../api/api'
import Cookies from 'js-cookie'

export default function UsersPage() {
    const [users, setUsers] = useState<any[]>([]) // Usando any para o tipo dos usuários
    const [error, setError] = useState<string | null>(null) // Para exibir possíveis erros

    useEffect(() => {
        const fetchUsers = async () => {
            const token = Cookies.get('token') // Pegando o token dos cookies
            if (!token) {
                console.error('Token não encontrado!')
                setError('Token não encontrado!')
                return
            }

            console.log('Token:', token) // Adicionando log para verificação

            try {
                const response = await api.get('/users', {
                    headers: {
                        Authorization: `Bearer ${token}`, // Enviando o token no header
                    },
                })
                console.log('Usuários:', response.data) // Verificando a resposta da API
                setUsers(response.data) // Atualizando a lista de usuários
            } catch (error) {
                console.error('Erro ao buscar usuários:', error)
                setError('Erro ao buscar usuários.')
            }
        }

        fetchUsers()
    }, []) // O array vazio faz a requisição ser feita uma vez, ao carregar a página

    return (
        <ProtectedRoute>
            <h1 className="text-3xl font-bold mb-4">Lista de Usuários</h1>
            {error && <p className="text-red-500">{error}</p>}{' '}
            {/* Exibindo erros se houver */}
            <ul>
                {users.length > 0 ? (
                    users.map((user) => (
                        <li key={user.id} className="border p-2 mb-2">
                            {user.name} - {user.email}
                        </li>
                    ))
                ) : (
                    <p>Nenhum usuário encontrado.</p>
                )}
            </ul>
        </ProtectedRoute>
    )
}
