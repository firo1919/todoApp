"use client";

import { registerUser } from "@/actions/userActions";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { UserRegister, UserRegisterSchema } from "@/lib/zod/UserRegisterSchema";
import { zodResolver } from "@hookform/resolvers/zod";
import { Eye, EyeOff, Lock, Mail, User } from "lucide-react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import LoadingSpinner from "../ui/LoadingSpinner";

export function RegisterForm() {
	const [showPassword, setShowPassword] = useState(false);
	const [showConfirmPassword, setShowConfirmPassword] = useState(false);
	const [isLoading, setIsLoading] = useState(false);
	const {
		register,
		handleSubmit,
		formState: { errors },
	} = useForm<UserRegister>({ resolver: zodResolver(UserRegisterSchema) });
	const router = useRouter();

	async function onSubmit(data: UserRegister) {
		setIsLoading(true);
		console.log(data);
		const user = {
			username: data.username,
			firstName: data.firstname,
			lastName: data.lastname,
			password: data.password,
		};
		try {
			const response = await registerUser(user);
			if (!response?.status) {
				toast.error(response?.message);
			} else {
				toast.success("Registration successful!");
				router.replace("/login");
			}
		} catch (error) {
			console.log(error);
			toast.error("Registration failed, try again");
		}
		setIsLoading(false);
	}

	return (
		<Card className="w-full max-w-md mx-auto relative">
			<CardHeader className="space-y-1">
				<CardTitle className="text-2xl font-bold text-center">Create Account</CardTitle>
				<CardDescription className="text-center">Sign up to get started with your tasks</CardDescription>
			</CardHeader>
			<CardContent>
				<form className="space-y-4" onSubmit={handleSubmit(onSubmit)}>
					<div className="space-y-2">
						<Label htmlFor="firstname">First Name</Label>
						<div className="relative">
							<User className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
							<Input
								id="firstname"
								type="text"
								placeholder="Enter your first name"
								className="pl-10"
								{...register("firstname")}
							/>
						</div>
						{errors.firstname && <p className="text-red-600 text-sm">{errors.firstname.message}</p>}
					</div>
					<div className="space-y-2">
						<Label htmlFor="lastname">Last Name</Label>
						<div className="relative">
							<User className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
							<Input
								id="lastname"
								type="text"
								placeholder="Enter your last name"
								className="pl-10"
								{...register("lastname")}
							/>
						</div>
						{errors.lastname && <p className="text-red-600 text-sm">{errors.lastname.message}</p>}
					</div>
					<div className="space-y-2">
						<Label htmlFor="username">User name</Label>
						<div className="relative">
							<Mail className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
							<Input
								id="username"
								type="text"
								placeholder="Enter your username"
								className="pl-10"
								{...register("username")}
							/>
						</div>
						{errors.username && <p className="text-red-600 text-sm">{errors.username.message}</p>}
					</div>
					<div className="space-y-2">
						<Label htmlFor="password">Password</Label>
						<div className="relative">
							<Lock className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
							<Input
								id="password"
								type={showPassword ? "text" : "password"}
								placeholder="Create a password"
								className="pl-10 pr-10"
								{...register("password")}
							/>
							<Button
								type="button"
								variant="ghost"
								size="sm"
								className="absolute right-0 top-0 h-full px-3 py-2 hover:bg-transparent"
								onClick={() => setShowPassword(!showPassword)}
							>
								{showPassword ? (
									<EyeOff className="h-4 w-4 text-muted-foreground" />
								) : (
									<Eye className="h-4 w-4 text-muted-foreground" />
								)}
							</Button>
						</div>
						{errors.password && <p className="text-red-600 text-sm">{errors.password.message}</p>}
					</div>
					<div className="space-y-2">
						<Label htmlFor="confirmPassword">Confirm Password</Label>
						<div className="relative">
							<Lock className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
							<Input
								id="confirmPassword"
								type={showConfirmPassword ? "text" : "password"}
								placeholder="Confirm your password"
								className="pl-10 pr-10"
								{...register("confirmPassword")}
							/>
							<Button
								type="button"
								variant="ghost"
								size="sm"
								className="absolute right-0 top-0 h-full px-3 py-2 hover:bg-transparent"
								onClick={() => setShowConfirmPassword(!showConfirmPassword)}
							>
								{showConfirmPassword ? (
									<EyeOff className="h-4 w-4 text-muted-foreground" />
								) : (
									<Eye className="h-4 w-4 text-muted-foreground" />
								)}
							</Button>
						</div>
						{errors.confirmPassword && (
							<p className="text-red-600 text-sm">{errors.confirmPassword.message}</p>
						)}
					</div>
					<Button type="submit" className="w-full">
						Create Account
					</Button>
				</form>
				<div className="mt-4 text-center text-sm">
					<span className="text-muted-foreground">Already have an account? </span>
					<Link href="/login" className="text-primary hover:underline">
						Sign in
					</Link>
				</div>
			</CardContent>
			{isLoading && <LoadingSpinner />}
		</Card>
	);
}
