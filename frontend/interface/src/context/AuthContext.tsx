'use client'
import { createContext, useContext, useState, useEffect } from 'react'
import Cookies from 'js-cookie'
import api from '../api/api'

interface AuthContextType {
    user: string | null
    login: (email: string, senha: string) => Promise<boolean>
    logout: () => void
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    const [user, setUser] = useState<string | null>(null)

    useEffect(() => {
        const token = Cookies.get('token')
        if (token) {
            const storedUser = Cookies.get('user')
            if (storedUser) {
                setUser(storedUser)
            }
        }
    }, [])

    const login = async (email: string, senha: string) => {
        try {
            const response = await api.post('/auth/login', { email, senha })
            console.log('Headers recebidos:', response.headers)

            // Pegando o token do header
            const authHeader = response.headers['authorization']

            if (!authHeader || !authHeader.startsWith('Bearer ')) {
                console.error('Token não encontrado no header da resposta.')
                return false
            }

            const token = authHeader.split(' ')[1] // Extraindo o token corretamente

            // Salvando o token nos cookies
            Cookies.set('token', token, { expires: 1 }) // Expira em 1 dia
            Cookies.set('user', email, { expires: 1 })
            setUser(email)

            return true
        } catch (error) {
            console.error('Erro no login:', error)
            return false
        }
    }

    const logout = () => {
        Cookies.remove('token')
        Cookies.remove('user')
        setUser(null)
    }

    return (
        <AuthContext.Provider value={{ user, login, logout }}>
            {children}
        </AuthContext.Provider>
    )
}

export const useAuth = () => {
    const context = useContext(AuthContext)
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider')
    }
    return context
}
