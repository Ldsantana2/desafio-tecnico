import { NextResponse } from 'next/server'
import type { NextRequest } from 'next/server'

export function middleware(req: NextRequest) {
    const token = req.cookies.get('token')

    // Se não houver token e tentar acessar uma página protegida, redireciona para login
    if (!token && req.nextUrl.pathname.startsWith('/users')) {
        return NextResponse.redirect(new URL('/login', req.url))
    }

    return NextResponse.next()
}

export const config = {
    matcher: ['/users/:path*'], // Protege todas as rotas dentro de /users
}
