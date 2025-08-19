import { jwtDecode } from "jwt-decode";
import NextAuth from "next-auth";
import Credentials from "next-auth/providers/credentials";
import { UserLoginSchema } from "./lib/zod/UserLoginSchema";
import { loginUser, refreshToken } from "./utils/authUtils";

export const { handlers, signIn, signOut, auth } = NextAuth({
	providers: [
		Credentials({
			credentials: {
				username: {},
				password: {},
			},

			authorize: async (credentials) => {
				console.log("Started to authenticate user");
				try {
					const { username, password } = await UserLoginSchema.parseAsync(credentials);
					const response = await loginUser({
						username: username,
						password: password,
					});
					let user = null;
					if (response?.status) {
						const expiresIn = jwtDecode(response.data.accessToken).exp;
						user = {
							id: response.data.id,
							name: response.data.username,
							accessToken: response.data.accessToken,
							refreshToken: response.data.refreshToken,
							role: response.data.role,
							expiresIn: expiresIn,
						};
					}
					return user;
				} catch (error) {
					console.log("Error occured during authentication", error);
				}

				return null;
			},
		}),
	],
	callbacks: {
		authorized: async ({ auth }) => {
			console.log("Checking authorization status:", auth);
			return !!auth?.user;
		},
		async jwt({ token, user, account }) {
			if (account) {
				token.role = user.role;
				token.name = user.name;
				token.accessToken = user.accessToken;
				token.refreshToken = user.refreshToken;
				token.expiresIn = user.expiresIn;
				return token;
			} else if (token.expiresIn && Date.now() < token.expiresIn * 1000) {
				console.log("access token is active until ", new Date(token.expiresIn * 1000).toTimeString());
				return token;
			} else {
				console.log("Refreshing access Token");
				try {
					const tokens = await refreshToken({ refreshToken: token.refreshToken });

					if (!tokens || !tokens.status) {
						console.log("Error refreshing token", tokens);
						return null;
					}
					const expiresIn = jwtDecode(tokens.data.accessToken).exp;
					token.accessToken = tokens.data.accessToken;
					token.expiresIn = expiresIn;
					console.log("New access token generated");
				} catch (error) {
					console.error("Error refreshing access_token", error);
					token.error = "RefreshTokenError";
				}
				return token;
			}
		},
		session({ session, token }) {
			if (session) {
				session.user.role = token.role;
				session.user.name = token.name;
				session.user.accessToken = token.accessToken;
			}
			return session;
		},
	},
	pages: {
		signIn: "/login",
	},
	session: {
		strategy: "jwt",
	},
	trustHost: true,
});
