import React, { createContext, useState, useEffect, ReactNode } from 'react'
import Cookies from 'js-cookie'
import { useRouter } from 'next/router'
import api from '../api/api'

interface AuthContextType {
    user: { email: string | null }
    setUser: React.Dispatch<React.SetStateAction<{ email: string | null }>>
    login: (email: string, senha: string) => Promise<boolean>
    logout: () => void
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)

export const AuthProvider = ({ children }: { children: ReactNode }) => {
    const [user, setUser] = useState<{ email: string | null }>({ email: null })
    const router = useRouter()

    useEffect(() => {
        const token = Cookies.get('token')
        const savedUserEmail = Cookies.get('user')

        if (token && savedUserEmail) {
            setUser({ email: savedUserEmail })
        }
    }, [])

    const login = async (email: string, senha: string): Promise<boolean> => {
        try {
            const response = await api.post('/auth/login', { email, senha })

            const authHeader = response.headers['authorization']

            if (!authHeader || !authHeader.startsWith('Bearer ')) {
                console.error('Token não encontrado no header da resposta.')
                return false
            }

            const token = authHeader.split(' ')[1]

            Cookies.set('token', token, { expires: 1 })
            Cookies.set('user', email, { expires: 1 })
            setUser({ email })

            router.push('/profile')
            return true
        } catch (error) {
            console.error('Erro no login:', error)
            return false
        }
    }

    const logout = () => {
        Cookies.remove('token')
        Cookies.remove('user')
        setUser({ email: null })
        router.push('/login')
    }

    return (
        <AuthContext.Provider value={{ user, setUser, login, logout }}>
            {children}
        </AuthContext.Provider>
    )
}

export const useAuth = (): AuthContextType => {
    const context = React.useContext(AuthContext)
    if (!context) {
        throw new Error('useAuth deve ser usado dentro de um AuthProvider')
    }
    return context
}
