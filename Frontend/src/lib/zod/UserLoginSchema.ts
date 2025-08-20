import * as z from "zod";

export const UserLoginSchema = z.object({
	username: z.string().min(1, "Username is required"),
	password: z.string().min(1, "Username is required"),
});

export type UserLogin = z.infer<typeof UserLoginSchema>;
