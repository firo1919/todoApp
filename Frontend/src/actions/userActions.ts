"use server";

import { RegisterResponse, RegisterUser } from "@/types/authTypes";

const baseUrl = process.env.API_URL;

export async function registerUser(user: RegisterUser) {
	const url = `${baseUrl}/api/auth/register`;
	console.log("Attempting to register user", url);
	try {
		const response = await fetch(url, {
			headers: { "Content-Type": "application/json" },
			method: "POST",
			body: JSON.stringify(user),
		});

		const responseData: RegisterResponse = await response.json();
		return responseData;
	} catch (error) {
		console.log("Failed logging in user", error);
	}
	return null;
}
