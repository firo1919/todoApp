import { UserLogin } from "@/lib/zod/UserLoginSchema";
import { LoginResponse, RefreshToken, RefreshTokenResponse } from "@/types/authTypes";

const baseUrl = process.env.API_URL;

export async function loginUser(data: UserLogin): Promise<LoginResponse | null> {
	const url = `${baseUrl}/api/auth/login`;
	console.log("Attempting to login user", url);
	try {
		const response = await fetch(url, {
			headers: { "Content-Type": "application/json" },
			method: "POST",
			body: JSON.stringify(data),
		});

		const responseData: LoginResponse = await response.json();
		return responseData;
	} catch (error) {
		console.log("Failed logging in user", error);
	}
	return null;
}

export async function refreshToken(data: RefreshToken): Promise<RefreshTokenResponse | null> {
	const url = `${baseUrl}/api/auth/refresh`;
	console.log("Attempting to refresh token", url);
	try {
		const response = await fetch(url, {
			headers: { "Content-Type": "application/json" },
			method: "POST",
			body: JSON.stringify(data),
		});

		const responseData: RefreshTokenResponse = await response.json();
		return responseData;
	} catch (error) {
		console.log("Failed refreshing token", error);
	}
	return null;
}
