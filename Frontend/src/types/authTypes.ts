export type LoginResponse = {
	status: boolean;
	message: string;
	data: {
		id: string;
		username: string;
		active: boolean;
		role: string;
		accessToken: string;
		refreshToken: string;
	};
};

export type RegisterResponse = {
	status: boolean;
	message: string;
	data: {
		id: string;
		firstName: string;
		lastName: string;
		username: string;
		active: boolean;
		role: string;
	};
};

export type RefreshTokenResponse = {
	status: boolean;
	message: string;
	data: {
		id: string;
		username: string;
		active: boolean;
		role: string;
		accessToken: string;
		refreshToken: string;
	};
};

export type RefreshToken = {
	refreshToken: string;
};

export type RegisterUser = {
	username: string;
	firstName: string;
	lastName: string;
	password: string;
};
