import { auth } from "@/auth";
const publicRoutes = ["/", "/login", "/register", "/denied", "/home"];
const rolePaths = {
	USER: "/user",
	ADMIN: "/admin",
};
export default auth((req) => {
	const isAuthenticated = !!req.auth;
	const isPublicRoute = publicRoutes.includes(req.nextUrl.pathname);
	const root = new URL("/", req.nextUrl.origin);
	const role = req.auth?.user.role;

	if (!isAuthenticated && !isPublicRoute) {
		return Response.redirect(root);
	}
	if (isAuthenticated && !isPublicRoute) {
		if (role && !req.nextUrl.pathname.startsWith(rolePaths[role as keyof typeof rolePaths])) {
			return Response.redirect(new URL("/denied", req.nextUrl.origin));
		}
	}
});

export const config = {
	matcher: ["/((?!api|_next/static|_next/image|favicon.ico|images).*)"],
};
