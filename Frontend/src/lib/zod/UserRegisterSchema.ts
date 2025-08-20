import * as z from "zod";

export const UserRegisterSchema = z
	.object({
		firstname: z.string().min(1, "First name is required"),
		lastname: z.string().min(1, "Last name is required"),
		username: z.string().min(1, "User name is required"),
		password: z.string().min(6, "Password must be at least 6 characters"),
		confirmPassword: z.string(),
	})
	.refine((form) => form.confirmPassword === form.password, {
		message: "Passwords do not match",
		path: ["confirmPassword"],
	});

export type UserRegister = z.infer<typeof UserRegisterSchema>;
