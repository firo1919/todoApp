"use client";

import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { UserLogin, UserLoginSchema } from "@/lib/zod/UserLoginSchema";
import { zodResolver } from "@hookform/resolvers/zod";
import { Eye, EyeOff, Lock, Mail } from "lucide-react";
import { signIn } from "next-auth/react";
import Link from "next/link";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import LoadingSpinner from "../ui/LoadingSpinner";
interface Props {
	error: string;
}
export function LoginForm({ error }: Props) {
	const [showPassword, setShowPassword] = useState(false);
	const [isLoading, setIsLoading] = useState(false);
	const {
		register,
		handleSubmit,
		formState: { errors },
	} = useForm<UserLogin>({ resolver: zodResolver(UserLoginSchema) });

	useEffect(() => {
		if (error) {
			toast.error("Invalid Credentials, try again");
		}
	}, [error]);

	async function onSubmit(data: UserLogin) {
		setIsLoading(true);
		console.log(data);
		try {
			await signIn("credentials", { ...data, redirectTo: "/" });
		} catch (error) {
			console.log(error);
			toast.error("Login failed, try again");
		}
		setIsLoading(false);
	}
	return (
		<Card className="w-full max-w-md mx-auto relative">
			<CardHeader className="space-y-1">
				<CardTitle className="text-2xl font-bold text-center">Welcome Back</CardTitle>
				<CardDescription className="text-center">Sign in to your account to continue</CardDescription>
			</CardHeader>
			<CardContent>
				<form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
					<div className="space-y-2">
						<Label htmlFor="username">User name</Label>
						<div className="relative">
							<Mail className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
							<Input
								id="username"
								type="text"
								placeholder="Enter your user name"
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
								placeholder="Enter your password"
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
					<Button type="submit" className="w-full">
						Sign In
					</Button>
				</form>
				<div className="mt-4 text-center text-sm">
					<span className="text-muted-foreground">Don&apos;t have an account? </span>
					<Link href="/register" className="text-primary hover:underline">
						Sign up
					</Link>
				</div>
			</CardContent>
			{isLoading && <LoadingSpinner />}
		</Card>
	);
}
