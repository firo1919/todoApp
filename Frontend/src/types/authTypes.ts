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
