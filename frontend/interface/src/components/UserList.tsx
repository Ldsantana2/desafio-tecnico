// src/components/UsersList.tsx
import { useEffect, useState } from 'react'
import Cookies from 'js-cookie'
import api from '../api/api'

const UsersList = () => {
    const [users, setUsers] = useState<any[]>([]) // Para armazenar a lista de usuários
    const [error, setError] = useState<string | null>(null) // Para armazenar erros, se houver

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
        <div>
            {error && <p className="text-red-500">{error}</p>}{' '}
            {/* Exibindo o erro, se houver */}
            <ul>
                {users.length > 0 ? (
                    users.map((user) => (
                        <li key={user.id}>
                            {user.name} - {user.email}
                        </li>
                    ))
                ) : (
                    <p>Nenhum usuário encontrado.</p>
                )}
            </ul>
        </div>
    )
}

export default UsersList
